package dao.interfaces;

import java.util.List;
import model.Ventaa;

public interface IVentaDao {

    boolean actualizar(Ventaa venta);

    boolean actualizarEstado(int id, Ventaa.Estado nuevo);

    boolean eliminar(int id);

    boolean insertar(Ventaa venta);

    List<Ventaa> obtenerPorCliente(int idCliente);

    Ventaa obtenerPorId(int id);

    List<Ventaa> obtenerTodos();
    
}
