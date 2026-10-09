package view;

/**
 *
 * @author saras
 */
public class MenuCliente {
    Validaciones v = new Validaciones();
     public int opcionTipoLogIn() {

        return v.validarEntero("""
                               ╭─────────────────╮
                               │             ⋆｡°✩TECNOSTORE⋆｡°✩             │
                               │       ── Qué haremos hoy? ──         │
                               ├─────────────────┤
                               │                                                                    │
                               │   1.  Registrarse                                        │
                               │   2.  Iniciar sesion                                    │
                               │   3.  Salir                                                    │
                               │                                                                    │
                               ├─────────────────┤
                               │   Ingresa una opción:                             │
                               ╰─────────────────╯
                               """);

    }
     
      public int OpcionAccion() {

        return v.validarEntero("""
                               
                               ╭─────────────────╮
                               │             ⋆｡°✩TECNOSTORE⋆｡°✩             │
                               │          ─Qué te gustaría hacer?─          │
                               ├───────────────── ┤
                               │         1.  Carrito de compras                    │
                               │         2.  Actualizar mi perfil                    │
                               │         3. Ver mi perfil                                 │
                               │         4. Eliminar mi cuenta                      │
                               │         5. Salir                                               │
                               │                                                                    │
                               ├─────────────────┤
                               │   Ingresa una opción:                             │
                               ╰─────────────────╯
                               
                               """);

    }
      
     public int opcionGestionPedido() {

        return v.validarEntero("""
                               ╭─────────────────╮
                               │             ⋆｡°✩TECNOSTORE⋆｡°✩            │
                               │          ─Qué te gustaría hacer?─         │
                               ├─────────────────┤
                               │         1.  Crear un pedido                         │
                               │         2.  Actualizar mi pedido                │
                               │         3. Ver mis pedidos                         │
                               │         4. Eliminar pedido                          │
                               │         5. Salir                                               │
                               │                                                                    │
                               ├─────────────────┤
                               │   Ingresa una opción:                             │
                               ╰─────────────────╯
                               
                               """);

    }
     
     public int OpcionActualizarPedido() {

        return v.validarEntero("""
                               1. Agregar celular
                               2. Eliminar celular
                               3. Cancelar pedido
                               4. Salir
                               """);

    }
     
      public int OpcionActualizarDatosPersonales() {

        return v.validarEntero("""
                               1. Nombre
                               2. Apellido
                               3. Email
                               4. Identificacion
                               5.Telefono
                               6. Salir
                               """);

    }
}
