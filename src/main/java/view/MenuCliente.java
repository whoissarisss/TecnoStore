package view;

import view.Validaciones.Entero;


public class MenuCliente {
  Entero v = new Entero();
     public int opcionTipoLogIn() {
        return v.validarEntero("""
            ========================================
                           TECNOSTORE
                            Menú Login
            ========================================
             1. Registrarse
             2. Iniciar sesión
             3. Salir
            ========================================
             ¡Hola! ¿Ya tienes cuenta o es tu primera vez aqui?
               Ingresa una opción:
            ========================================
                    
                    """);
    }
     
      public int OpcionAccion() {
        return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Qué deseas hacer?
           ========================================
             1. Carrito de compras
             2. Actualizar mi perfil
             3. Ver mi perfil
             4. Eliminar mi cuenta
             5. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }
      
     public int opcionGestionPedido() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                      ¿Que quieres hacer con tu pedido?
           ========================================
             1. Crear un pedido
             2. Actualizar mi pedido
             3. Ver mis pedidos
             4. Eliminar pedido
             5. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }
    
      public int OpcionActualizarPedido() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Que cambio deseas realizar?
           ========================================
             1. Agregar celular
             2. Eliminar celular
             3. Cancelar pedido
             4. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }

    public int OpcionActualizarDatosPersonales() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                        ¿Que dato deseas actualizar?
           ========================================
             1. Nombre
             2. Apellido
             3. Email
             4. Identificacion
             5. Telefono
             6. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }
}
