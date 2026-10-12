package model;

import java.util.Arrays;
import view.Marco;

public class Administradorr extends Personaa {
    private String usuario;
    private String contraseña;

    public Administradorr(String nombre, String apellido, String identificacion, String email, String telefono, String usuario, String contraseña) {
        super(nombre, apellido, identificacion, email, telefono);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Administradorr(int id, String nombre, String apellido, String identificacion, String email, String telefono, String usuario, String contraseña) {
        super(id, nombre, apellido, identificacion, email, telefono);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
  
@Override
protected String titulo() {
    return "ADMINISTRADOR";
}

@Override
protected String[][] campos() {
    String[][] base = super.campos();
    String[][] todos = Arrays.copyOf(base, base.length + 1);
    todos[base.length] = Marco.fila("User", usuario);
    return todos;
}
}
