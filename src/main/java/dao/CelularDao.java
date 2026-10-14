package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Celularr;
import model.Marcaa;


public class CelularDao {

    private final Conexion conexion = Conexion.getInstancia();
    private final MarcaDao marcaDao = new MarcaDao();

    private static final String SELECT_BASE = """
            SELECT id, id_marca, modelo, sistemaOperativo, gama, precio, stock
            FROM celular
            """;

   
    private Celularr mapear(ResultSet rs) throws SQLException {
        Marcaa marca = marcaDao.obtenerPorId(rs.getInt("id_marca"));

        return new Celularr(
                rs.getInt("id"),
                rs.getInt("stock"),
                rs.getString("modelo"),
                rs.getDouble("precio"),
                marca,
                Celularr.SistemaOperativo.valueOf(rs.getString("sistemaOperativo")),
                Celularr.Gama.valueOf(rs.getString("gama")));
    }




    public boolean insertar(Celularr celular) {
        String sql = """
                INSERT INTO celular (id_marca, modelo, sistemaOperativo, gama, precio, stock)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, celular.getMarca().getId());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo().name());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    celular.setId(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar celular: " + e.getMessage());
        }
        return false;
    }



    public Celularr obtenerPorId(int id) {
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
            System.err.println("Error al buscar celular: " + e.getMessage());
        }
        return null;
    }

    public List<Celularr> obtenerTodos() {
        List<Celularr> celulares = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY id";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                celulares.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar celulares: " + e.getMessage());
        }
        return celulares;
    }

   

    public boolean actualizar(Celularr celular) {
        String sql = """
                UPDATE celular SET id_marca = ?, modelo = ?, sistemaOperativo = ?,
                                   gama = ?, precio = ?, stock = ?
                WHERE id = ?
                """;

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, celular.getMarca().getId());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo().name());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.setInt(7, celular.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar celular: " + e.getMessage());
        }
        return false;
    }


    public boolean descontarStock(Connection c, int idCelular, int cantidad) throws SQLException {
        String sql = "UPDATE celular SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idCelular);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        }
    }

 
    public boolean reponerStock(Connection c, int idCelular, int cantidad) throws SQLException {
        String sql = "UPDATE celular SET stock = stock + ? WHERE id = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idCelular);
            return ps.executeUpdate() > 0;
        }
    }

   
    public boolean eliminar(int id) {
        String sql = "DELETE FROM celular WHERE id = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar celular: " + e.getMessage());
        }
        return false;
    }
}
