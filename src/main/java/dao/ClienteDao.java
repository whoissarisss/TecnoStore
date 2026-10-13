package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Clientee;


public class ClienteDao {

    private final Conexion conexion = new Conexion();
    private final PersonaDao personaDao = new PersonaDao();


    private static final String SELECT_BASE = """
            SELECT p.id, p.nombre, p.apellido, p.identificacion, p.email, p.telefono
            FROM cliente cl
            JOIN persona p ON p.id = cl.id
            """;

    private Clientee mapear(ResultSet rs) throws SQLException {
        return new Clientee(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("identificacion"),
                rs.getString("email"),
                rs.getString("telefono"));
    }

 


    public boolean insertar(Clientee cliente) {
        String sql = "INSERT INTO cliente (id) VALUES (?)";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                if (!personaDao.insertar(c, cliente)) {
                    throw new SQLException("No se pudo insertar la persona");
                }

                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, cliente.getId());
                    ps.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
        return false;
    }



    public Clientee obtenerPorId(int id) {
        return obtenerUno(SELECT_BASE + " WHERE p.id = ?", id);
    }

    // Para iniciar sesión: el cliente con esa identificación, o null si no existe
    public Clientee obtenerPorIdentificacion(String identificacion) {
        return obtenerUno(SELECT_BASE + " WHERE p.identificacion = ?", identificacion);
    }

    public List<Clientee> obtenerTodos() {
        List<Clientee> clientes = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY p.apellido, p.nombre";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    
    private Clientee obtenerUno(String sql, Object parametro) {
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }



    public boolean existeEmail(String email) {
        return personaDao.existeEmail(email);
    }

    public boolean existeIdentificacion(String identificacion) {
        return personaDao.existeIdentificacion(identificacion);
    }

    public boolean existeEmailDeOtro(String email, int id) {
        return personaDao.existeEmailDeOtra(email, id);
    }

    public boolean existeIdentificacionDeOtro(String identificacion, int id) {
        return personaDao.existeIdentificacionDeOtra(identificacion, id);
    }




    public boolean actualizar(Clientee cliente) {
        return personaDao.actualizar(cliente);
    }


    public boolean eliminar(int id) {
        return personaDao.eliminar(id);
    }
}
