package view;

import view.Validaciones.Entero;

public class MenuAdministrador {
    Entero v = new Entero(); 
   public int escogerGestionar() {
        return v.validarEntero(Marco.menu(
            "Panel Administrador", "¿Qué área deseas gestionar?",
            "Gestionar Clientes", "Gestionar Celulares", "Gestionar Ventas",
            "Reportes y Análisis", "Salir"));
    }
 
    
    public int escogerGestionarCliente() {
        return v.validarEntero(Marco.menu(
            "Gestión de Clientes", "¿Qué deseas hacer?",
            "Listar clientes", "Eliminar cliente", "Salir"));
    }

     
     public int escogerGestionarCelular() {
        return v.validarEntero(Marco.menu(
            "Gestión de Celulares", "¿Qué deseas hacer con\nel inventario?",
            "Agregar celular", "Actualizar celular", "Listar celulares",
            "Eliminar celular", "Salir"));
    } 
      
     public int escogerGestionarVenta() {
        return v.validarEntero(Marco.menu(
            "Gestión de Ventas", "¿Qué deseas hacer con\nlas ventas?",
            "Actualizar estado", "Listar ventas", "Eliminar venta", "Salir"));
    }
               
         public int escogerActualizarCelular() {
        return v.validarEntero(Marco.menu(
            "Actualizar Celular", "¿Qué dato deseas actualizar?",
            "Modelo", "Stock", "Marca", "Sistema Operativo",
            "Gama", "Precio", "Salir"));
    }
         public int escogerReporte() {
    return v.validarEntero(Marco.menu(
        "Reportes y Análisis", "¿Qué reporte deseas ver?",
        "Celulares con stock bajo", "Top 3 más vendidos", "Ventas totales por mes",
        "Generar reporte_ventas.txt", "Historial de auditoría", "Salir"));
}
}