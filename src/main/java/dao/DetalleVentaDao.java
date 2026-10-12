package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Celularr;
import model.DetalleVentaa;

public class DetalleVentaDao {

    Conexion c = new Conexion();
    CelularDao daoC = new CelularDao();
    AuditoriaDao audit = new AuditoriaDao();

    public List<DetalleVentaa> listarPorVenta(int idVenta) {
        List<DetalleVentaa> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select * from detalle_venta where id_venta=? order by id");
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Celularr cel = daoC.buscar(rs.getInt("id_celular"));
                lista.add(new DetalleVentaa(rs.getInt("id"), cel, rs.getInt("cantidad"), rs.getDouble("subtotal")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar los detalles: " + e.getMessage());
        }
        return lista;
    }

    // Si el celular ya esta en el pedido, suma la cantidad en vez de repetir la fila
    public void agregar(int idVenta, DetalleVentaa d) {
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                if (!ventaPendiente(con, idVenta)) {
                    System.out.println("Solo se pueden modificar pedidos pendientes!");
                    con.rollback();
                    return;
                }
                PreparedStatement ps = con.prepareStatement(
                        "select id from detalle_venta where id_venta=? and id_celular=?");
                ps.setInt(1, idVenta);
                ps.setInt(2, d.getCelular().getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    PreparedStatement up = con.prepareStatement(
                            "update detalle_venta set cantidad = cantidad + ?, subtotal = subtotal + ? where id=?");
                    up.setInt(1, d.getCantidad());
                    up.setDouble(2, d.getSubtotal());
                    up.setInt(3, rs.getInt("id"));
                    up.executeUpdate();
                } else {
                    PreparedStatement ins = con.prepareStatement(
                            "insert into detalle_venta (id_venta, id_celular, cantidad, subtotal) values (?,?,?,?)");
                    ins.setInt(1, idVenta);
                    ins.setInt(2, d.getCelular().getId());
                    ins.setInt(3, d.getCantidad());
                    ins.setDouble(4, d.getSubtotal());
                    ins.executeUpdate();
                }
                recalcularTotal(con, idVenta);
                con.commit();
                System.out.println("Celular agregado al pedido!");
                audit.registrar("AGREGAR_ITEM", "venta", idVenta,
                        d.getCantidad() + "x " + d.getCelular().getModelo());
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(int idVenta, int idDetalle) {
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                if (!ventaPendiente(con, idVenta)) {
                    System.out.println("Solo se pueden modificar pedidos pendientes!");
                    con.rollback();
                    return;
                }
                PreparedStatement ps = con.prepareStatement("delete from detalle_venta where id=? and id_venta=?");
                ps.setInt(1, idDetalle);
                ps.setInt(2, idVenta);
                if (ps.executeUpdate() == 0) {
                    System.out.println("Ese detalle no pertenece al pedido");
                    con.rollback();
                    return;
                }
                recalcularTotal(con, idVenta);
                con.commit();
                System.out.println("Celular eliminado del pedido!");
                audit.registrar("QUITAR_ITEM", "venta", idVenta, "detalle #" + idDetalle);
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    private boolean ventaPendiente(Connection con, int idVenta) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select estado from venta where id=?");
        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getString("estado").equals("PENDIENTE");
    }

    private void recalcularTotal(Connection con, int idVenta) throws SQLException {
        String sql = "update venta set total = "
                + "(select coalesce(sum(subtotal), 0) from detalle_venta where id_venta=?) where id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idVenta);
        ps.setInt(2, idVenta);
        ps.executeUpdate();
    }
}

