package controller.admin;

import dao.AdministradorDao;
import model.Administradorr;
import view.MenuAdministrador;
import view.Validaciones.Texto;

public class GestionAdministrador {

    AdministradorDao dao = new AdministradorDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();
    Texto texto = new Texto();

    private boolean ingresar() {
        for (int intento = 1; intento <= 3; intento++) {
            String usuario = texto.validarTexto("Usuario");
            String clave = texto.validarTexto("Contraseña");
            Administradorr admin = dao.obtenerPorUsuario(usuario);
            if (admin != null && admin.getContraseña().equals(clave)) {
                System.out.println("Bienvenid@, " + admin.getNombre() + "!");
                return true;
            }
            System.out.println("Usuario o contraseña incorrectos (intento " + intento + " de 3)");
        }
        return false;
    }

    public void menu() {
        if (!ingresar()) {
            System.out.println("Demasiados intentos fallidos.");
            return;
        }
        int op;
        do {
            op = menuAdmin.escogerGestionar();
            switch (op) {
                case 1 -> new GestionCliente().menu();
                case 2 -> new GestionCelular().menu();
                case 3 -> new GestionVenta().menu();
                case 4 -> System.out.println("Disponible pronto...");
                case 5 -> System.out.println("Cerrando sesion...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 5);
    }
}