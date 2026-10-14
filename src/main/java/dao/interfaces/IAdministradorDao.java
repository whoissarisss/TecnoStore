package dao.interfaces;

import model.Administradorr;

public interface IAdministradorDao {

    Administradorr obtenerPorUsuario(String usuario);
    
}
