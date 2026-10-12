package model;

import view.Marco;

public class Personaa {
    private int id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;
    private String telefono;

    public Personaa(int id, String nombre, String apellido, String identificacion, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.email = email;
        this.telefono = telefono;
    }

    public Personaa(String nombre, String apellido, String identificacion, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.email = email;
        this.telefono = telefono;
    }
    
    

    public int getId() {
        return id;
    }
    
    public void setId (int id) {
         this.id= id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
  protected String titulo() {
    return "PERSONA";
}

protected String[][] campos() {
    return new String[][]{
        Marco.fila("ID", id),
        Marco.fila("Name", nombre + " " + apellido),
        Marco.fila("Doc", identificacion),
        Marco.fila("Email", email),
        Marco.fila("Phone", telefono)
    };
}

@Override
public String toString() {
    return Marco.tarjeta(titulo(), campos());
}
}