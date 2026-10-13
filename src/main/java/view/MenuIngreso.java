package view;
 
import view.Validaciones.Entero;


public class MenuIngreso {
    Entero v = new Entero();

    public int escogerPersona() {
        return v.validarEntero(Marco.menu(
            "Menú Principal", "¡Hola! ¿Con quién\nhablamos hoy?",
            "Administrador", "Cliente", "Salir"));
    }
}