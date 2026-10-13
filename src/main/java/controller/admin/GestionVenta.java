package controller.admin;

import dao.VentaDao;
import model.Ventaa;
import view.MenuAdministrador;
import view.Validaciones.Confirmacion;
import view.Validaciones.Entero;
import view.Validaciones.EnteroRango;

public class GestionVenta {

    VentaDao dao = new VentaDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();
    Entero entero = new Entero();
    EnteroRango rango = new EnteroRango();
    Confirmacion confirmacion = new Confirmacion();

    private void listar() {
        var lista = dao.obtenerTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        }
        for (Ventaa v : lista) {
            System.out.println(v);
        }
    }

    private void actualizarEstado() {
        listar();
        int id = entero.validarEntero("Ingrese el id de la venta");
        if (dao.obtenerPorId(id) == null) {
            System.out.println("No existe dicha entidad");
            return;
        }
        int op = rango.validarEnteroRango("Nuevo estado: 1. PENDIENTE  2. PAGADA  3. CANCELADA", 1, 3);
        dao.actualizarEstado(id, Ventaa.Estado.values()[op - 1]);
    }

    private void eliminar() {
        listar();
        int id = entero.validarEntero("Ingrese el id a eliminar");
        if (confirmacion.validarConfirmacion("¿Desea eliminar la venta?")) {
            dao.eliminar(id);
        } else {
            System.out.println("Operacion cancelada correctamente!");
        }
    }

    public void menu() {
        int op;
        do {
            op = menuAdmin.escogerGestionarVenta();
            switch (op) {
                case 1 -> actualizarEstado();
                case 2 -> listar();
                case 3 -> eliminar();
                case 4 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 4);
    }
}