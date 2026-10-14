package dao.interfaces;

import java.util.List;
import model.Marcaa;


public interface IMarcaDao {

    boolean insertarMarca(Marcaa marca);

    Marcaa obtenerPorId(int id);

    List<Marcaa> obtenerTodos();
    
}
