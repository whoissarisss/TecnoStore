/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import model.Personaa;

/**
 *
 * @author saras
 */
public interface IPersonaDao {

    boolean actualizar(Personaa p);

    boolean eliminar(int id);

    boolean existeEmail(String email);

    boolean existeEmailDeOtra(String email, int id);

    boolean existeIdentificacion(String identificacion);

    boolean existeIdentificacionDeOtra(String identificacion, int id);

    boolean insertar(Connection c, Personaa p) throws SQLException;
    
}
