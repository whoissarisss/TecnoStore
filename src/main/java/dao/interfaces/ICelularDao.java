package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Celularr;

public interface ICelularDao {

    boolean actualizar(Celularr celular);

    boolean descontarStock(Connection c, int idCelular, int cantidad) throws SQLException;

    boolean eliminar(int id);

    boolean insertar(Celularr celular);

    Celularr obtenerPorId(int id);

    List<Celularr> obtenerTodos();

    boolean reponerStock(Connection c, int idCelular, int cantidad) throws SQLException;
    
}
