package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Marcaa;

public class MarcaDao {

    Conexion c = Conexion.getInstancia();
    AuditoriaDao audit = new AuditoriaDao();

    public void crear(Marcaa marca) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("insert into marca (nombre) values (?)");
            ps.setString(1, marca.getNombre());
            ps.executeUpdate();
            System.out.println("Marca creada correctamente!");
            audit.registrar("CREAR", "marca", null, marca.getNombre());
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public List<Marcaa> obtenerMarcas() {
        List<Marcaa> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select id, nombre from marca order by nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Marcaa(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar marcas: " + e.getMessage());
        }
        return lista;
    }

    public Marcaa buscar(int id) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select id, nombre from marca where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Marcaa(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la marca: " + e.getMessage());
        }
        return null;
    }
}