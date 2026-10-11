package view;

import view.Validaciones.Entero;


public class MenuCliente {
  Entero v = new Entero();
     public int opcionTipoLogIn() {
        return v.validarEntero(Marco.menu(
            "¿Qué haremos hoy?", "¿Ya tienes cuenta o es tu\nprimera vez aquí?",
            "Registrarse", "Iniciar sesión", "Salir"));
    }
     
      public int OpcionAccion() {
        return v.validarEntero(Marco.menu(
            "¡Bienvenid@! ¿Qué deseas?", "¿En qué te podemos ayudar hoy?",
            "Carrito de compras", "Actualizar mi perfil", "Ver mi perfil",
            "Eliminar mi cuenta", "Salir"));
    }
      
     public int opcionGestionPedido() {
        return v.validarEntero(Marco.menu(
            "Gestión de Pedidos", "¿Qué quieres hacer con tu\npedido hoy?",
            "Crear un pedido", "Actualizar mi pedido", "Ver mis pedidos",
            "Eliminar pedido", "Salir"));
    }
    
      public int OpcionActualizarPedido() {
        return v.validarEntero(Marco.menu(
            "Actualizar Pedido", "¿Qué cambio deseas realizar?",
            "Agregar celular", "Eliminar celular", "Cancelar pedido", "Salir"));
    }

    public int OpcionActualizarDatosPersonales() {
        return v.validarEntero(Marco.menu(
            "Actualizar Mis Datos", "¿Qué dato deseas actualizar?",
            "Nombre", "Apellido", "Email", "Identificación", "Teléfono", "Salir"));
    }
}
