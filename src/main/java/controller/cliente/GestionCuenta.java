package controller.cliente;

import dao.ClienteDao;
import dao.PersonaDao;
import model.Clientee;
import view.MenuCliente;
import view.Validaciones.Confirmacion;
import view.Validaciones.Correo;
import view.Validaciones.Documento;
import view.Validaciones.SoloLetras;
import view.Validaciones.Telefono;


public class GestionCuenta {

    ClienteDao dao = new ClienteDao();
    PersonaDao daoP = new PersonaDao();
    MenuCliente menuCliente = new MenuCliente();
    SoloLetras letras = new SoloLetras();
    Documento documento = new Documento();
    Correo correo = new Correo();
    Telefono telefono = new Telefono();
    Confirmacion confirmacion = new Confirmacion();

    private void registrar() {
        String identificacion = documento.validarDocumento("Ingrese su identificación");
        if (daoP.existeIdentificacion(identificacion)) {
            System.out.println("Ya existe una cuenta con esa identificacion");
            return;
        }
        String email = correo.validarCorreo("Ingrese su correo");
        if (daoP.existeEmail(email)) {
            System.out.println("Ya existe una cuenta con ese correo");
            return;
        }
        String nombre = letras.validarSoloLetras("Ingrese su nombre");
        String apellido = letras.validarSoloLetras("Ingrese su apellido");
        String tel = telefono.validarTelefono("Ingrese su teléfono");

        dao.insertar(new Clientee(nombre, apellido, identificacion, email, tel));
        if (dao.obtenerPorIdentificacion(identificacion) != null) {
            System.out.println("Ya puede iniciar sesion con su identificacion y su correo.");
        }
    }

    private Clientee ingresar() {
        for (int intento = 1; intento <= 3; intento++) {
            String identificacion = documento.validarDocumento("Ingrese su identificación");
            String email = correo.validarCorreo("Ingrese su correo");
            Clientee cl = dao.obtenerPorIdentificacion(identificacion);
            if (cl != null && cl.getEmail() != null && cl.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Bienvenid@, " + cl.getNombre() + "!");
                return cl;
            }
            System.out.println("Identificacion o correo incorrectos (intento " + intento + " de 3)");
        }
        return null;
    }

    private void actualizarPerfil(Clientee cl) {
        boolean cancelar = false;
        int op = menuCliente.OpcionActualizarDatosPersonales();
        switch (op) {
            case 1 -> cl.setNombre(letras.validarSoloLetras("Ingrese el nuevo nombre"));
            case 2 -> cl.setApellido(letras.validarSoloLetras("Ingrese el nuevo apellido"));
            case 3 -> {
                String nuevo = correo.validarCorreo("Ingrese el nuevo correo");
                if (!nuevo.equalsIgnoreCase(cl.getEmail()) && daoP.existeEmail(nuevo)) {
                    System.out.println("Ya existe una persona con ese correo");
                    cancelar = true;
                } else {
                    cl.setEmail(nuevo);
                }
            }
            case 4 -> {
                String nueva = documento.validarDocumento("Ingrese la nueva identificación");
                if (!nueva.equals(cl.getIdentificacion()) && daoP.existeIdentificacion(nueva)) {
                    System.out.println("Ya existe una persona con esa identificacion");
                    cancelar = true;
                } else {
                    cl.setIdentificacion(nueva);
                }
            }
            case 5 -> cl.setTelefono(telefono.validarTelefono("Ingrese el nuevo teléfono"));
            case 6 -> {
                System.out.println("Operacion cancelada");
                cancelar = true;
            }
            default -> {
                System.out.println("Opcion no encontrada!");
                cancelar = true;
            }
        }
        if (cancelar == false) {
            dao.actualizar(cl);
        }
    }

    private void panel(Clientee cl) {
        boolean cuentaEliminada = false;
        int op;
        do {
            op = menuCliente.OpcionAccion();
            switch (op) {
                case 1 -> new GestionPedido(cl).menu();
                case 2 -> actualizarPerfil(cl);
                case 3 -> System.out.println(cl);
                case 4 -> {
                    if (confirmacion.validarConfirmacion("¿Seguro que desea eliminar su cuenta?")) {
                        dao.eliminar(cl.getId());
                        cuentaEliminada = dao.obtenerPorId(cl.getId()) == null;
                    }
                }
                case 5 -> System.out.println("Cerrando sesion...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 5 && !cuentaEliminada);
    }

 
    public void menu() {
        int op;
        do {
            op = menuCliente.opcionTipoLogIn();
            switch (op) {
                case 1 -> registrar();
                case 2 -> {
                    Clientee cl = ingresar();
                    if (cl == null) {
                        System.out.println("Demasiados intentos fallidos.");
                    } else {
                        panel(cl);
                    }
                }
                case 3 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 3);
    }
}

