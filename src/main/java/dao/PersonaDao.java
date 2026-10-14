package dao;

import dao.interfaces.IPersonaDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Personaa;


public class PersonaDao implements IPersonaDao {

    private final Conexion conexion = new Conexion();
 

    @Override
    public boolean insertar(Connection c, Personaa p) throws SQLException {
        String sql = """
                INSERT INTO persona (nombre, apellido, identificacion, email, telefono)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getIdentificacion());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelefono());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                    return true;
                }
            }
        }
        return false;
    }

 

    @Override
    public boolean actualizar(Personaa p) {
        String sql = """
                UPDATE persona SET nombre = ?, apellido = ?, identificacion = ?,
                                   email = ?, telefono = ?
                WHERE id = ?
                """;

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getIdentificacion());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelefono());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar persona: " + e.getMessage());
        }
        return false;
    }

 
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean existeEmail(String email) {
        return existe("SELECT 1 FROM persona WHERE email = ?", email);
    }

    @Override
    public boolean existeIdentificacion(String identificacion) {
        return existe("SELECT 1 FROM persona WHERE identificacion = ?", identificacion);
    }


    @Override
    public boolean existeEmailDeOtra(String email, int id) {
        return existe("SELECT 1 FROM persona WHERE email = ? AND id <> ?", email, id);
    }

 
    @Override
    public boolean existeIdentificacionDeOtra(String identificacion, int id) {
        return existe("SELECT 1 FROM persona WHERE identificacion = ? AND id <> ?", identificacion, id);
    }

    private boolean existe(String sql, Object... parametros) {
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar datos: " + e.getMessage());
        }
        return false;
    }
}
