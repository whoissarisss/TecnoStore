package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Administradorr;


public class AdministradorDao {

    private final Conexion conexion = new Conexion();

    private static final String SELECT_BASE = """
            SELECT p.id, p.nombre, p.apellido, p.identificacion, p.email, p.telefono, a.usuario, a.`contraseña` AS clave
            FROM administrador a
            JOIN persona p ON p.id = a.id
            """;

 
    public Administradorr obtenerPorUsuario(String usuario) {
        String sql = SELECT_BASE + " WHERE a.usuario = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Administradorr(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("identificacion"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getString("usuario"),
                            rs.getString("clave"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar administrador: " + e.getMessage());
        }
        return null;
    }
}
