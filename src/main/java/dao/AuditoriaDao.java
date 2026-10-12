package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Auditoria;
import model.Sesion;

public class AuditoriaDao {

   Conexion c = Conexion.getInstancia();

    public void registrar(String accion, String entidad, Integer idEntidad, String detalle) {
        try (Connection con = c.conexion()) {
            String sql = "insert into auditoria (usuario, accion, entidad, id_entidad, detalle) "
                    + "values (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Sesion.usuario);
            ps.setString(2, accion);
            ps.setString(3, entidad);
            if (idEntidad == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, idEntidad);
            }
            ps.setString(5, detalle);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("No se pudo registrar la auditoria: " + e.getMessage());
        }
    }

    // Los 100 movimientos mas recientes
    public List<Auditoria> obtenerRegistros() {
        List<Auditoria> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select * from auditoria order by id desc limit 100");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Auditoria(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("usuario"),
                        rs.getString("accion"),
                        rs.getString("entidad"),
                        rs.getObject("id_entidad", Integer.class),
                        rs.getString("detalle")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar la auditoria: " + e.getMessage());
        }
        return lista;
    }
}

