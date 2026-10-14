package dao.interfaces;

import java.util.List;
import model.Clientee;

public interface IClienteDao {

    boolean actualizar(Clientee cliente);

    boolean eliminar(int id);

    boolean existeEmail(String email);

    boolean existeEmailDeOtro(String email, int id);

    boolean existeIdentificacion(String identificacion);

    boolean existeIdentificacionDeOtro(String identificacion, int id);

    boolean insertar(Clientee cliente);

    Clientee obtenerPorId(int id);

    Clientee obtenerPorIdentificacion(String identificacion);

    List<Clientee> obtenerTodos();
    
}
