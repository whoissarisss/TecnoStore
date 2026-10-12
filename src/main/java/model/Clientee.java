package model;

import view.Marco;

public class Clientee extends Personaa {
    
    public Clientee(int id, String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(id, nombre, apellido, identificacion, email, Telefono);
    }

    public Clientee(String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(nombre, apellido, identificacion, email, Telefono);
    }

    @Override
    public String toString() {
         return Marco.tarjeta("CLIENTE", 
                 Marco.fila("ID", getId()),
                 Marco.fila("Name", getNombre() + " " + getApellido()),
                 Marco.fila("Doc", getIdentificacion()),
                Marco.fila("Email", getEmail()),
                Marco.fila("Phone", getTelefono()));
    }
}
