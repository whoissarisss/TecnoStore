package controller.admin;

import dao.ClienteDao;
import model.Clientee;
import view.MenuAdministrador;
import view.Validaciones.Confirmacion;
import view.Validaciones.Entero;

public class GestionCliente {

    ClienteDao dao = new ClienteDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();
    Entero entero = new Entero();
    Confirmacion confirmacion = new Confirmacion();

    private void listar() {
        var lista = dao.obtenerTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        }
        for (Clientee cl : lista) {
            System.out.println(cl);
        }
    }

    private void eliminar() {
        listar();
        int id = entero.validarEntero("Ingrese el id a eliminar");
        if (confirmacion.validarConfirmacion("¿Desea eliminar el cliente?")) {
            dao.eliminar(id);
        } else {
            System.out.println("Operacion cancelada correctamente!");
        }
    }

    public void menu() {
        int op;
        do {
            op = menuAdmin.escogerGestionarCliente();
            switch (op) {
                case 1 -> listar();
                case 2 -> eliminar();
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 3);
    }
}

