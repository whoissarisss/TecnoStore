package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Marcaa;

public class MarcaDao {

    private final Conexion conexion = Conexion.getInstancia();

    private static final String SELECT_BASE = """
            SELECT id, nombre
            FROM marca
            """;

    private Marcaa mapear(ResultSet rs) throws SQLException {
        return new Marcaa(rs.getInt("id"), rs.getString("nombre"));
    }

  
    public boolean insertarMarca(Marcaa marca) {
        String sql = "INSERT INTO marca (nombre) VALUES (?)";
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, marca.getNombre());
            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    marca.setId(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar marca: " + e.getMessage());
        }
        return false;
    }

  
    public Marcaa obtenerPorId(int id) {
        String sql = SELECT_BASE + " WHERE id = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar marca: " + e.getMessage());
        }
        return null;
    }

    public List<Marcaa> obtenerTodos() {
        List<Marcaa> marcas = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY nombre";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                marcas.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar marcas: " + e.getMessage());
        }
        return marcas;
    }
}

