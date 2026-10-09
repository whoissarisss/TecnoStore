package model;

public class Administradorr extends Personaa {
    private final int idAdministrador = 0;
    private String usuario;
    private String contraseña;

    public Administradorr(String usuario, String contraseña, int idAdministrador, String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(idAdministrador, nombre, apellido, identificacion, email, Telefono);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Administradorr(String usuario, String contraseña, String nombre, String apellido, String identificacion, String email, String Telefono) {
        super(nombre, apellido, identificacion, email, Telefono);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public int getIdAdministrador() {
        return idAdministrador;
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
