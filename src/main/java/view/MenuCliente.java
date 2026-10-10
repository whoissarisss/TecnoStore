package view;

import view.Validaciones.Entero;


public class MenuCliente {
  Entero v = new Entero();
     public int opcionTipoLogIn() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │       ── ¿Qué haremos hoy? ──        │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Registrarse                    │
                │   2.  Iniciar sesión                 │
                │   3.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Ya tienes cuenta o es tu        │
                │     primera vez aquí?                │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
     
      public int OpcionAccion() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │     ── ¡Bienvenid@! ¿Qué deseas? ── │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Carrito de compras             │
                │   2.  Actualizar mi perfil           │
                │   3.  Ver mi perfil                  │
                │   4.  Eliminar mi cuenta             │
                │   5.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿En qué te podemos ayudar hoy?  │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
      
     public int opcionGestionPedido() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │      ── Gestión de Pedidos ──        │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Crear un pedido                │
                │   2.  Actualizar mi pedido           │
                │   3.  Ver mis pedidos                │
                │   4.  Eliminar pedido                │
                │   5.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué quieres hacer con tu        │
                │     pedido hoy?                      │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
     
     public int OpcionActualizarPedido() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │      ── Actualizar Pedido ──         │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Agregar celular                │
                │   2.  Eliminar celular               │
                │   3.  Cancelar pedido                │
                │   4.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué cambio deseas realizar?    │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);
    }
     
      public int OpcionActualizarDatosPersonales() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │     ── Actualizar Mis Datos ──       │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Nombre                         │
                │   2.  Apellido                       │
                │   3.  Email                          │
                │   4.  Identificación                 │
                │   5.  Teléfono                       │
                │   6.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué dato deseas actualizar?    │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
}
