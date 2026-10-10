package view;

import view.Validaciones.Entero;

public class MenuAdministrador {
    Entero v = new Entero();
    public int escogerGestionar() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │      ── Panel Administrador ──       │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Gestionar Clientes             │
                │   2.  Gestionar Celulares            │
                │   3.  Gestionar Ventas               │
                │   4.  Reportes y Análisis            │
                │   5.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué área deseas gestionar?     │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
    
     public int escogerGestionarCliente() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │       ── Gestión de Clientes ──      │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Listar clientes                │
                │   2.  Eliminar cliente               │
                │   3.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué deseas hacer?              │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
     
      public int escogerGestionarCelular() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │      ── Gestión de Celulares ──      │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Agregar celular                │
                │   2.  Actualizar celular             │
                │   3.  Listar celulares               │
                │   4.  Eliminar celular               │
                │   5.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué deseas hacer con            │
                │     el inventario?                   │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
      
       public int escogerGestionarVenta() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │       ── Gestión de Ventas ──        │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Actualizar estado              │
                │   2.  Listar ventas                  │
                │   3.  Eliminar venta                 │
                │   4.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué deseas hacer con            │
                │     las ventas?                      │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
               
         public int escogerActualizarCelular() {

        return v.validarEntero("""
                ╭──────────────────────────────────────╮
                │    ⋆｡°✩  T E C N O S T O R E  ✩°｡⋆   │
                │       ── Actualizar Celular ──       │
                ├──────────────────────────────────────┤
                │                                      │
                │   1.  Modelo                         │
                │   2.  Stock                          │
                │   3.  Marca                          │
                │   4.  Sistema Operativo              │
                │   5.  Gama                           │
                │   6.  Precio                         │
                │   7.  Salir                          │
                │                                      │
                ├──────────────────────────────────────┤
                │   ▸ ¿Qué dato deseas actualizar?    │
                │     Ingresa una opción:              │
                ╰──────────────────────────────────────╯
                """);

    }
}
