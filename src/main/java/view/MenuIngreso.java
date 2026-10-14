package view;
 
import view.Validaciones.Entero;


public class MenuIngreso {
    Entero v = new Entero();
    
        public int escogerPersona() {
    return v.validarEntero("""
            ========================================
                           TECNOSTORE
                         Menú Principal
            ========================================
             1. Administrador
             2. Cliente
             3. Salir
            ========================================
                ¡Hola! ¿Con quién hablamos hoy?
                       Ingresa una opción:
            ========================================
                           """);
}
    }
