package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Celularr;
import model.DetalleVentaa;


public class DetalleVentaDao {

    private final Conexion conexion = new Conexion();
    private final CelularDao celularDao = new CelularDao();

    public boolean insertar(Connection c, DetalleVentaa detalle, int idVenta) throws SQLException {
        String sql = """
                INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idVenta);
            ps.setInt(2, detalle.getCelular().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubtotal());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    detalle.setId(keys.getInt(1));
                }
            }
            return true;
        }
    }


    public List<DetalleVentaa> obtenerPorVenta(int idVenta) {
        List<DetalleVentaa> detalles = new ArrayList<>();
        String sql = "SELECT id, id_celular, cantidad, subtotal FROM detalle_venta WHERE id_venta = ? ORDER BY id";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Celularr celular = celularDao.obtenerPorId(rs.getInt("id_celular"));
                    detalles.add(new DetalleVentaa(
                            rs.getInt("id"),
                            celular,
                            rs.getInt("cantidad"),
                            rs.getDouble("subtotal")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los detalles: " + e.getMessage());
        }
        return detalles;
    }


    public boolean eliminarPorVenta(Connection c, int idVenta) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM detalle_venta WHERE id_venta = ?")) {
            ps.setInt(1, idVenta);
            ps.executeUpdate();
            return true;
        }
    }
}
