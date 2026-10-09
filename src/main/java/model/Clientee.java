package model;

public class Clientee extends Personaa {
    
    private final int idCliente = 0;

    public Clientee(int idCliente, String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(idCliente, nombre, apellido, identificacion, email, Telefono);
    }

    public Clientee(String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(nombre, apellido, identificacion, email, Telefono);
    }

    public int getIdCliente() {
        return idCliente;
    }
    
    
}
