package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // La unica instancia, creada una sola vez al cargar la clase
    private static final Conexion INSTANCIA = new Conexion();

    private static final String URL = "jdbc:mysql://localhost:3306/tecnoStore_SaraRamirez";
    private static final String USUARIO = "root";
    private static final String CLAVE = "Sp210608*";

    // Constructor privado: nadie mas puede hacer "new Conexion()"
    private Conexion() {}

    public static Conexion getInstancia() {
        return INSTANCIA;
    }

    // Cada llamada entrega una conexion nueva, para poder cerrarla con try-with-resources
    public Connection conexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}