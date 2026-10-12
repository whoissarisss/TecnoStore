package model;


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
    
}
