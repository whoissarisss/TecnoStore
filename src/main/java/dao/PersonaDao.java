package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Personaa;

public class PersonaDao {

    Conexion c = new Conexion();

    int insertar(Connection con, Personaa p) throws SQLException {
        String sql = "insert into persona (nombre, apellido, identificacion, email, telefono) "
                + "values (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getApellido());
        ps.setString(3, p.getIdentificacion());
        ps.setString(4, p.getEmail());
        ps.setString(5, p.getTelefono());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            p.setId(rs.getInt(1));
            return p.getId();
        }
        throw new SQLException("No se pudo obtener el id de la persona");
    }

    void actualizar(Connection con, Personaa p) throws SQLException {
        String sql = "update persona set nombre=?, apellido=?, identificacion=?, email=?, telefono=? where id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getApellido());
        ps.setString(3, p.getIdentificacion());
        ps.setString(4, p.getEmail());
        ps.setString(5, p.getTelefono());
        ps.setInt(6, p.getId());
        ps.executeUpdate();
    }

    public boolean existeIdentificacion(String identificacion) {
        return existe("select 1 from persona where identificacion=? limit 1", identificacion);
    }

    public boolean existeEmail(String email) {
        return existe("select 1 from persona where email=? limit 1", email);
    }

    private boolean existe(String sql, String valor) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, valor);
            return ps.executeQuery().next();
        } catch (Exception e) {
            System.out.println("Error al validar: " + e.getMessage());
        }
        return false;
    }
}
