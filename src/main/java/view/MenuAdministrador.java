
package view;


public class MenuAdministrador {
    Validaciones v = new Validaciones();
    public int escogerGestionar() {

        return v.validarEntero("""
                               1. Gestionar Clientes
                               2. Gestionar Celulares
                               3. Gestionar Ventas
                               4. Reportes y Análisis
                               5. Salir
                               """);

    }
    
     public int escogerGestionarCliente() {

        return v.validarEntero("""
                               1. Listar
                               2. Eliminar
                               3. Salir
                               """);

    }
     
      public int escogerGestionarCelular() {

        return v.validarEntero("""
                               1. Agregar
                               2. Actualizar
                               3. Listar
                               4. Eliminar
                               5. Salir
                               """);

    }
      
       public int escogerGestionarVenta() {

        return v.validarEntero("""
                               1. Actualizar estado
                               2. Listar
                               3. Eliminar
                               4. Salir
                               """);

    }
               
         public int escogerActualizarCelular() {

        return v.validarEntero("""
                               1. Modelo
                               2. Stock
                               3. Marca
                               4. Sistema Operativo
                               5. Gama
                               6. Precio
                               7. Salir
                               """);

    }
}
