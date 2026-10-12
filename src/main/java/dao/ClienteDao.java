package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Clientee;

public class ClienteDao {

    Conexion c = Conexion.getInstancia();
    PersonaDao daoP = new PersonaDao();
    AuditoriaDao audit = new AuditoriaDao();

    private static final String SELECT = "select p.id, p.nombre, p.apellido, p.identificacion, "
            + "p.email, p.telefono from persona p join cliente cl on cl.id = p.id";

    public boolean validarExistenciaForaneaVenta(int id) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement("select 1 from venta where id_cliente=? limit 1");
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (Exception e) {
            System.out.println("Error al validar: " + e.getMessage());
        }
        return false;
    }

    public void crear(Clientee cliente) {
        try (Connection con = c.conexion()) {
            con.setAutoCommit(false);
            try {
                int id = daoP.insertar(con, cliente);
                PreparedStatement ps = con.prepareStatement("insert into cliente (id) values (?)");
                ps.setInt(1, id);
                ps.executeUpdate();
                con.commit();
                System.out.println("Cliente creado correctamente!");
                audit.registrar("CREAR", "cliente", id,
                        cliente.getNombre() + " " + cliente.getApellido());
            } catch (SQLException e) {
                con.rollback();   // si algo falla, no queda una persona huerfana
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Clientee cliente) {
        try (Connection con = c.conexion()) {
            daoP.actualizar(con, cliente);
            System.out.println("Cliente actualizado correctamente!");
            audit.registrar("ACTUALIZAR", "cliente", cliente.getId(),
                    cliente.getNombre() + " " + cliente.getApellido());
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try (Connection con = c.conexion()) {
            Clientee cliente = buscar(id);
            if (cliente == null) {
                System.out.println("Operacion cancelada, el id no existe");
            } else if (validarExistenciaForaneaVenta(id)) {
                System.out.println("Este cliente no puede ser eliminado, tiene ventas registradas!");
            } else {
                // Se borra la persona; el ON DELETE CASCADE elimina la fila de cliente
                PreparedStatement ps = con.prepareStatement("delete from persona where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Cliente eliminado correctamente!");
                audit.registrar("ELIMINAR", "cliente", id,
                        cliente.getNombre() + " " + cliente.getApellido());
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public List<Clientee> obtenerClientes() {
        List<Clientee> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement(SELECT + " order by p.apellido, p.nombre");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public Clientee buscar(int id) {
        try (Connection con = c.conexion()) {
            PreparedStatement ps = con.prepareStatement(SELECT + " where p.id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        }
        return null;
    }

    private Clientee mapear(ResultSet rs) throws SQLException {
        return new Clientee(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
                rs.getString("identificacion"), rs.getString("email"), rs.getString("telefono"));
    }
    
    public Clientee buscarPorIdentificacion(String identificacion) {
    try (Connection con = c.conexion()) {
        PreparedStatement ps = con.prepareStatement(SELECT + " where p.identificacion=?");
        ps.setString(1, identificacion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapear(rs);
        }
    } catch (Exception e) {
        System.out.println("Error al buscar el cliente: " + e.getMessage());
    }
    return null;
}

}