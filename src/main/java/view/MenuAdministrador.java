package view;

import view.Validaciones.Entero;

public class MenuAdministrador {
    Entero v = new Entero(); 
   public int escogerGestionar() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                        ¿Que area deseas gestionar?
           ========================================
             1. Gestionar Clientes
             2. Gestionar Celulares
             3. Gestionar Ventas
             4. Reportes y Análisis
             5. Salir
           ========================================
               Ingresa una opción:
           ========================================
        """);
    }
 
    
    public int escogerGestionarCliente() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Qué deseas hacer?
           ========================================
             1. Listar clientes
             2. Eliminar cliente
             3. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }

     
     public int escogerGestionarCelular() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                      ¿Qué deseas hacer con el inventario?
           ========================================
             1. Agregar celular
             2. Actualizar celular
             3. Listar celulares
             4. Eliminar celular
             5. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    } 
      
     public int escogerGestionarVenta() {
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Que deseas hacer con las ventas?
           ========================================
             1. Actualizar estado
             2. Listar ventas
             3. Eliminar venta
             4. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }
               
         public int escogerActualizarCelular() {     
         return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Qué dato deseas actualizar?
           ========================================
             1. Modelo
             2. Stock
             3. Marca
             4. Sistema Operativo
             5. Gama
             6. Precio
             7. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
    }
         public int escogerReporte() {
       return v.validarEntero("""
           ========================================
                              TECNOSTORE
                         ¿Que reporte deseas ver?
           ========================================
             1. Celulares con stock bajo
             2. Top 3 mas vendidos
             3. Ventas totales por mes
             4. Generar reporte_ventas.txt
             5. Salir
           ========================================
              ¡Hola! ¿En que te podemos ayudar hoy?
               Ingresa una opción:
           ========================================
        """);
}
}