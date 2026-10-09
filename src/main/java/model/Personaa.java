package model;

public class Personaa {
    private int id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;
    private String Telefono;

    public Personaa(int id, String nombre, String apellido, String identificacion, String email, String Telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.email = email;
        this.Telefono = Telefono;
    }

    public Personaa(String nombre, String apellido, String identificacion, String email, String Telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.email = email;
        this.Telefono = Telefono;
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
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    
}