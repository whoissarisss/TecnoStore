package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Clientee;
import model.DetalleVentaa;
import model.Ventaa;

public class VentaDao {

    Conexion c = new Conexion();
    ClienteDao daoCl = new ClienteDao();
    DetalleVentaDao daoD = new DetalleVentaDao();
    AuditoriaDao audit = new AuditoriaDao();

   
    public void crear(Ventaa venta) {
        if (venta.getDetalles().isEmpty()) {
            System.out.println("El pedido debe tener al menos un celular");
            return;
        }
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                String sql = "insert into venta (id_cliente, fecha, total, estado) values (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, venta.getCliente().getId());
                ps.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
                ps.setDouble(3, venta.getTotal());
                ps.setString(4, venta.getEstado().name());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("No se pudo obtener el id de la venta");
                }
                venta.setId(rs.getInt(1));

                PreparedStatement psDet = con.prepareStatement(
                        "insert into detalle_venta (id_venta, id_celular, cantidad, subtotal) values (?,?,?,?)");
                for (DetalleVentaa d : venta.getDetalles()) {
                    psDet.setInt(1, venta.getId());
                    psDet.setInt(2, d.getCelular().getId());
                    psDet.setInt(3, d.getCantidad());
                    psDet.setDouble(4, d.getSubtotal());
                    psDet.executeUpdate();
                }
                con.commit();
                System.out.println("Pedido creado correctamente!");
                audit.registrar("CREAR", "venta", venta.getId(),
                        "cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizarEstado(int id, Ventaa.Estado nuevo) {
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                PreparedStatement ps = con.prepareStatement("select estado from venta where id=? for update");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("Operacion cancelada, el id no existe");
                    con.rollback();
                    return;
                }
                Ventaa.Estado actual = Ventaa.Estado.valueOf(rs.getString("estado"));
                boolean valido = (actual == Ventaa.Estado.PENDIENTE && nuevo != Ventaa.Estado.PENDIENTE)
                        || (actual == Ventaa.Estado.PAGADA && nuevo == Ventaa.Estado.CANCELADA);
                if (!valido) {
                    System.out.println("Cambio de estado no permitido: " + actual + " -> " + nuevo);
                    con.rollback();
                    return;
                }

                if (actual == Ventaa.Estado.PENDIENTE && nuevo == Ventaa.Estado.PAGADA) {
                    moverStock(con, id, true);      // descuenta
                } else if (actual == Ventaa.Estado.PAGADA && nuevo == Ventaa.Estado.CANCELADA) {
                    moverStock(con, id, false);     // devuelve
                }

                PreparedStatement up = con.prepareStatement("update venta set estado=? where id=?");
                up.setString(1, nuevo.name());
                up.setInt(2, id);
                up.executeUpdate();
                con.commit();
                System.out.println("Estado actualizado: " + actual + " -> " + nuevo);
                audit.registrar("ESTADO", "venta", id, actual + " -> " + nuevo);
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el estado: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try (Connection con = c.conexion()) {
            Ventaa venta = buscar(id);
            if (venta == null) {
                System.out.println("Operacion cancelada, el id no existe");
            } else if (venta.getEstado() == Ventaa.Estado.PAGADA) {
                System.out.println("Una venta pagada no se puede eliminar, cancelela primero para devolver el stock!");
            } else {
                // Los detalles se borran solos por el ON DELETE CASCADE
                PreparedStatement ps = con.prepareStatement("delete from venta where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Venta eliminada correctamente!");
                audit.registrar("ELIMINAR", "venta", id, "estado: " + venta.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public List<Ventaa> obtenerVentas() {
        return consultar("select * from venta order by id desc", 0);
    }

    public List<Ventaa> listarPorCliente(int idCliente) {
        return consultar("select * from venta where id_cliente=? order by id desc", idCliente);
    }

    public Ventaa buscar(int id) {
        List<Ventaa> lista = consultar("select * from venta where id=?", id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    // parametro 0 = la consulta no recibe ningun "?"
    private List<Ventaa> consultar(String sql, int parametro) {
        List<Ventaa> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement(sql);
            if (sql.contains("?")) {
                ps.setInt(1, parametro);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar las ventas: " + e.getMessage());
        }
        return lista;
    }

    private Ventaa mapear(ResultSet rs) throws SQLException {
        Clientee cliente = daoCl.buscar(rs.getInt("id_cliente"));
        Ventaa venta = new Ventaa(rs.getInt("id"), cliente,
                rs.getTimestamp("fecha").toLocalDateTime(),
                Ventaa.Estado.valueOf(rs.getString("estado")));
        for (DetalleVentaa d : daoD.listarPorVenta(venta.getId())) {
            venta.agregarDetalle(d);
        }
        return venta;
    }

    // descontar = true resta el stock; false lo devuelve
    private void moverStock(Connection con, int idVenta, boolean descontar) throws SQLException {
        PreparedStatement det = con.prepareStatement(
                "select id_celular, cantidad from detalle_venta where id_venta=?");
        det.setInt(1, idVenta);
        ResultSet rs = det.executeQuery();

        String sql = descontar
                ? "update celular set stock = stock - ? where id=? and stock >= ?"
                : "update celular set stock = stock + ? where id=?";
        PreparedStatement up = con.prepareStatement(sql);
        while (rs.next()) {
            up.setInt(1, rs.getInt("cantidad"));
            up.setInt(2, rs.getInt("id_celular"));
            if (descontar) {
                up.setInt(3, rs.getInt("cantidad"));
            }
            if (up.executeUpdate() == 0) {
                throw new SQLException("Stock insuficiente para el celular con id " + rs.getInt("id_celular"));
            }
        }
    }
}

