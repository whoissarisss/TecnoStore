
package model;

public class Administrador extends Persona {

    private String contraseña;


    public Administrador() {
        super();
    }

   
    public Administrador(int id, String nombre, String identificacion,
                         String correo, String telefono,
                         String contrasena) {
        super(id, nombre, identificacion, correo, telefono);
        this.contraseña = contraseña;
    }

    // Getter
    public String getContraseña() {
        return contraseña;
    }

    // Setter
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
    
@Override
    public String toString() {
         return """
            
            ╭───────────────╮
            │          ✦  TECNOSTORE  ✦               │                                                                          
            │             ADMIN PROFILE                  │
            ├───────────────┤
            │                                                            │
            │   ♡ ID          : %-20s                         │
            │   ♡ Username    : %-20s                 │
            │   ♡ Password    : %-20s                  │
            │                                                            │
            ╰───────────────╯
            """.formatted(idAdmin, usuario, contraseña);
    }
}
