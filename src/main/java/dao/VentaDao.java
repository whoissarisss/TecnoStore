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

    private final Conexion conexion = Conexion.getInstancia();
    private final CelularDao celularDao = new CelularDao();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();


    private static final String SELECT_BASE = """
            SELECT v.id, v.fecha, v.estado,
                   p.id AS id_cliente, p.nombre, p.apellido,
                   p.identificacion, p.email, p.telefono
            FROM venta v
            JOIN cliente cl ON cl.id = v.id_cliente
            JOIN persona p ON p.id = cl.id
            """;


    private Ventaa mapear(ResultSet rs) throws SQLException {
        Clientee cliente = new Clientee(
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("identificacion"),
                rs.getString("email"),
                rs.getString("telefono"));

        int idVenta = rs.getInt("id");

        Ventaa venta = new Ventaa(
                idVenta,
                cliente,
                rs.getTimestamp("fecha").toLocalDateTime(),
                Ventaa.Estado.valueOf(rs.getString("estado")));

        for (DetalleVentaa d : detalleVentaDao.obtenerPorVenta(idVenta)) {
            venta.agregarDetalle(d);
        }
        return venta;
    }

 
    public boolean insertar(Ventaa venta) {
        String sql = """
                INSERT INTO venta (id_cliente, fecha, total, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, venta.getCliente().getId());
                    ps.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
                    ps.setDouble(3, venta.getTotal());
                    ps.setString(4, venta.getEstado().name());

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("No se pudo insertar la venta");
                    }
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("No se obtuvo el id de la venta");
                        }
                        venta.setId(keys.getInt(1));
                    }
                }
                for (DetalleVentaa d : venta.getDetalles()) {
                    detalleVentaDao.insertar(c, d, venta.getId());
                }
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar venta: " + e.getMessage());
        }
        return false;
    }

 

    public Ventaa obtenerPorId(int id) {
        String sql = SELECT_BASE + " WHERE v.id = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }

    public List<Ventaa> obtenerTodos() {
        return obtenerLista(SELECT_BASE + " ORDER BY v.id DESC", null);
    }


    public List<Ventaa> obtenerPorCliente(int idCliente) {
        return obtenerLista(SELECT_BASE + " WHERE v.id_cliente = ? ORDER BY v.id DESC", idCliente);
    }

    private List<Ventaa> obtenerLista(String sql, Object parametro) {
        List<Ventaa> ventas = new ArrayList<>();

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setObject(1, parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
        return ventas;
    }

  


    public boolean actualizar(Ventaa venta) {
        String sql = "UPDATE venta SET total = ? WHERE id = ? AND estado = 'PENDIENTE'";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setDouble(1, venta.getTotal());
                    ps.setInt(2, venta.getId());

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Solo se pueden modificar ventas pendientes");
                    }
                }

                detalleVentaDao.eliminarPorVenta(c, venta.getId());
                for (DetalleVentaa d : venta.getDetalles()) {
                    detalleVentaDao.insertar(c, d, venta.getId());
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar venta: " + e.getMessage());
        }
        return false;
    }


    public boolean actualizarEstado(int id, Ventaa.Estado nuevo) {
        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                Ventaa.Estado actual = leerEstado(c, id);

                boolean permitido = (actual == Ventaa.Estado.PENDIENTE && nuevo != Ventaa.Estado.PENDIENTE)
                        || (actual == Ventaa.Estado.PAGADA && nuevo == Ventaa.Estado.CANCELADA);
                if (!permitido) {
                    throw new SQLException("Cambio de estado no permitido: " + actual + " -> " + nuevo);
                }

                if (actual == Ventaa.Estado.PENDIENTE && nuevo == Ventaa.Estado.PAGADA) {
                    moverStock(c, id, true);     
                } else if (actual == Ventaa.Estado.PAGADA && nuevo == Ventaa.Estado.CANCELADA) {
                    moverStock(c, id, false); 
                }
                try (PreparedStatement ps = c.prepareStatement("UPDATE venta SET estado = ? WHERE id = ?")) {
                    ps.setString(1, nuevo.name());
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
        }
        return false;
    }


    private Ventaa.Estado leerEstado(Connection c, int id) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("SELECT estado FROM venta WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Ventaa.Estado.valueOf(rs.getString("estado"));
                }
            }
        }
        throw new SQLException("La venta " + id + " no existe");
    }


    private void moverStock(Connection c, int idVenta, boolean descontar) throws SQLException {
        String sql = "SELECT id_celular, cantidad FROM detalle_venta WHERE id_venta = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idCelular = rs.getInt("id_celular");
                    int cantidad = rs.getInt("cantidad");

                    boolean ok = descontar
                            ? celularDao.descontarStock(c, idCelular, cantidad)
                            : celularDao.reponerStock(c, idCelular, cantidad);

                    if (!ok) {
                        throw new SQLException("Stock insuficiente para el celular con id " + idCelular);
                    }
                }
            }
        }
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM venta WHERE id = ? AND estado <> 'PAGADA'";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
        }
        return false;
    }
}
