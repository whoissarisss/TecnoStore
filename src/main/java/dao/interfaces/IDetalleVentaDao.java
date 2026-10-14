package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.DetalleVentaa;

public interface IDetalleVentaDao {

    boolean eliminarPorVenta(Connection c, int idVenta) throws SQLException;

    boolean insertar(Connection c, DetalleVentaa detalle, int idVenta) throws SQLException;

    List<DetalleVentaa> obtenerPorVenta(int idVenta);
    
}
