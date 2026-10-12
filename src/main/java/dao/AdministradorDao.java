package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Administradorr;

public class AdministradorDao {

    Conexion c = Conexion.getInstancia();
    PersonaDao daoP = new PersonaDao();

    private static final String SELECT = "select p.id, p.nombre, p.apellido, p.identificacion, "
            + "p.email, p.telefono, a.usuario, a.`contraseña` as clave "
            + "from persona p join administrador a on a.id = p.id";

    public boolean hayAdministradores() {
        try (Connection con = c.conexion()) {
            ResultSet rs = con.prepareStatement("select 1 from administrador limit 1").executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al validar: " + e.getMessage());
        }
        return false;
    }

    public boolean existeUsuario(String usuario) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select 1 from administrador where usuario=? limit 1");
            ps.setString(1, usuario);
            return ps.executeQuery().next();
        } catch (Exception e) {
            System.out.println("Error al validar: " + e.getMessage());
        }
        return false;
    }

    // La contrasena debe llegar ya encriptada (ver Seguridad)
    public void crear(Administradorr admin) {
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                int id = daoP.insertar(con, admin);
                String sql = "insert into administrador (id, usuario, `contraseña`) values (?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, admin.getUsuario());
                ps.setString(3, admin.getContraseña());
                ps.executeUpdate();
                con.commit();
                System.out.println("Administrador creado correctamente!");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public Administradorr buscarPorUsuario(String usuario) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement(SELECT + " where a.usuario=?");
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Administradorr(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("identificacion"),
                        rs.getString("email"), rs.getString("telefono"),
                        rs.getString("usuario"), rs.getString("clave"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el administrador: " + e.getMessage());
        }
        return null;
    }
}
