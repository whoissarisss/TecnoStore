package model;

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

    // Filas extra de las clases hijas, ya con formato de caja (empiezan con salto de línea)
    protected String extra() {
        return "";
    }

    @Override
    public String toString() {
        String sub = " ".repeat((38 - titulo().length()) / 2) + titulo();
        return """
                ╭──────────────────────────────────────╮
                │              TECNOSTORE              │
                │%-38s│
                ├──────────────────────────────────────┤
                │                                      │
                │   * Id       : %-22s│
                │   * Nombre   : %-22.22s│
                │   * Doc      : %-22.22s│
                │   * Correo   : %-22.22s│
                │   * Telefono : %-22.22s│%s
                │                                      │
                ╰──────────────────────────────────────╯
                """.formatted(sub, id, nombre + " " + apellido, identificacion, email, telefono, extra());
    }
}
