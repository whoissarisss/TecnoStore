package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private static final Conexion INSTANCIA = Conexion.getInstancia();
    private Conexion() {}
    public static Conexion getInstancia() {
        return INSTANCIA;
    }
    
    public Connection conexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnoStore_SaraRamirez", "root", "Sp210608*");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}