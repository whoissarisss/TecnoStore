package model;

public class Administrador {
    private int idAdmin;
    private String usuario;
    private long contraseña;

    public Administrador(int idAdmin, String usuario, long contraseña) {
        this.idAdmin = idAdmin;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getContraseña() {
        return contraseña;
    }

    public void setContraseña(long contraseña) {
        this.contraseña = contraseña;
    }
 
@Override
    public String toString() {
        return """
              ===================================
                                         INFO ADMINISTRADOR
              ===================================
               Id:                                    %s                                                  
              ===================================
               Usuario:                          %s                                                 
              ===================================
               Contraseña:                    %s
               ===================================
               """.formatted( idAdmin, usuario, contraseña);
    }
    
}
