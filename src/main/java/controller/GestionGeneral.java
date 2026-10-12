package controller;

import controller.admin.GestionAdministrador;
import view.MenuIngreso;

public class GestionGeneral {

    MenuIngreso menuIngreso = new MenuIngreso();

    public void menuGeneral() {
        int op;
        do {
            op = menuIngreso.escogerPersona();
            switch (op) {
                case 1 -> new GestionAdministrador().menu();
                case 2 -> System.out.println("El area de clientes estara disponible pronto.");
                case 3 -> System.out.println("Gracias por usar TECNOSTORE");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 3);
    }
}

