package model;

public class Clientee extends Personaa {

    public Clientee(int id, String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(id, nombre, apellido, identificacion, email, Telefono);
    }

    public Clientee(String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(nombre, apellido, identificacion, email, Telefono);
    }
}
