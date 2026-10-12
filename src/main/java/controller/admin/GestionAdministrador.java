package controller.admin;

import dao.AdministradorDao;
import dao.AuditoriaDao;
import model.Administradorr;
import model.Sesion;
import view.MenuAdministrador;
import view.Validaciones.Texto;

public class GestionAdministrador {

    AdministradorDao dao = new AdministradorDao();
    AuditoriaDao audit = new AuditoriaDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();
    Texto texto = new Texto();

    private boolean ingresar() {
        for (int intento = 1; intento <= 3; intento++) {
            String usuario = texto.validarTexto("Usuario");
            String clave = texto.validarTexto("Contraseña");
            Administradorr admin = dao.buscarPorUsuario(usuario);
            if (admin != null && admin.getContraseña().equals(clave)) {
                Sesion.usuario = usuario;
                audit.registrar("LOGIN", "sesion", admin.getId(), null);
                System.out.println("Bienvenid@, " + admin.getNombre() + "!");
                return true;
            }
            audit.registrar("LOGIN_FALLIDO", "sesion", null, "usuario: " + usuario);
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
                case 5 -> {
                    audit.registrar("LOGOUT", "sesion", null, null);
                    Sesion.usuario = "sistema";
                    System.out.println("Cerrando sesion...");
                }
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 5);
    }
}