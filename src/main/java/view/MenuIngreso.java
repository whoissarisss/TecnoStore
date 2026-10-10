package view;
 
import view.Validaciones.Entero;


public class MenuIngreso {
    Entero v = new Entero();

    /*
    public Producto ingresarProducto() {
        return new Producto(0, v.validarTexto("Ingrese el nombre"),
                v.validarTexto("Ingrese la descripcion"),
                v.validarDecimal("Ingrese el monto de los ingredientes"),
                v.validarDecimal("Ingrese la mano de obra"));
    }

    public Precio ingresarPrecio() {
        return new Precio(0, v.validarTexto("Ingrese el nombre"),
                v.validarDecimal("Ingrese el monto"),
                null);
    }
*/
    
    public int escogerPersona() {

        return v.validarEntero("""
                ╭───────────────────╮
                │           ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆          │
                │                ── Menú Principal ──               │
                ├───────────────────┤
                │                                                                            │
                │   1.  Administrador                                          │
                │   2.  Cliente                                                       │
                │   3.  Salir                                                            │
                │                                                                            │
                ├───────────────────┤
                │   ▸ ¡Hola! ¿Con quién hablamos hoy?        │
                │     Ingresa una opción:                                   │
                ╰───────────────────╯
                """);

    }
}
