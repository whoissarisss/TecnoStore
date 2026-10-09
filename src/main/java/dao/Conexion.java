/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author saras
 */
public class Conexion {
    public Connection conectar(){
        Connection c = null;
        
        try{
            c= DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnoStore_SaraRamirez", "root", "Sp210608*");
            
            System.out.println("Conexion con la base de datos ha sido establecida exitosamente");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return c;
    }
}

