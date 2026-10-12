package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Celularr;
import model.Marcaa;

public class CelularDao {

    Conexion c = Conexion.getInstancia();
    MarcaDao daoM = new MarcaDao();
    AuditoriaDao audit = new AuditoriaDao();

    public boolean validarExistenciaForaneaVenta(int id) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select 1 from detalle_venta where id_celular=? limit 1");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al validar: " + e.getMessage());
        }
        return false;
    }

    public void crear(Celularr cel) {
        try (Connection con = c.conexion()) {
            String sql = "insert into celular (id_marca, modelo, sistemaOperativo, gama, precio, stock) "
                    + "values (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cel.getMarca().getId());
            ps.setString(2, cel.getModelo());
            ps.setString(3, cel.getSistemaOperativo().name());
            ps.setString(4, cel.getGama().name());
            ps.setDouble(5, cel.getPrecio());
            ps.setInt(6, cel.getStock());
            ps.executeUpdate();
            System.out.println("Celular creado correctamente!");
            audit.registrar("CREAR", "celular", null,
                    cel.getMarca().getNombre() + " " + cel.getModelo());
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Celularr cel) {
        try (Connection con = c.conexion()) {
            String sql = "update celular set id_marca=?, modelo=?, sistemaOperativo=?, gama=?, "
                    + "precio=?, stock=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cel.getMarca().getId());
            ps.setString(2, cel.getModelo());
            ps.setString(3, cel.getSistemaOperativo().name());
            ps.setString(4, cel.getGama().name());
            ps.setDouble(5, cel.getPrecio());
            ps.setInt(6, cel.getStock());
            ps.setInt(7, cel.getId());
            ps.executeUpdate();
            System.out.println("Celular actualizado correctamente!");
            audit.registrar("ACTUALIZAR", "celular", cel.getId(), cel.getModelo());
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try (Connection con = c.conexion()) {
            Celularr cel = buscar(id);
            if (cel == null) {
                System.out.println("Operacion cancelada, el id no existe");
            } else if (validarExistenciaForaneaVenta(id)) {
                System.out.println("Este celular no puede ser eliminado, hay ventas que lo referencian!");
            } else {
                PreparedStatement ps = con.prepareStatement("delete from celular where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Celular eliminado correctamente!");
                audit.registrar("ELIMINAR", "celular", id, cel.getModelo());
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public List<Celularr> obtenerCelulares() {
        List<Celularr> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select * from celular order by id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar celulares: " + e.getMessage());
        }
        return lista;
    }

    public Celularr buscar(int id) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select * from celular where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el celular: " + e.getMessage());
        }
        return null;
    }

    private Celularr mapear(ResultSet rs) throws Exception {
        Marcaa marca = daoM.buscar(rs.getInt("id_marca"));
        return new Celularr(
                rs.getInt("id"),
                rs.getInt("stock"),
                rs.getString("modelo"),
                rs.getDouble("precio"),
                marca,
                Celularr.SistemaOperativo.valueOf(rs.getString("sistemaOperativo")),
                Celularr.Gama.valueOf(rs.getString("gama")));
    }
}