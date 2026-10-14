package controller.admin;

import dao.CelularDao;
import dao.MarcaDao;
import model.Celularr;
import model.Marcaa;
import view.MenuAdministrador;
import view.Validaciones.Confirmacion;
import view.Validaciones.DecimalPositivo;
import view.Validaciones.Entero;
import view.Validaciones.EnteroRango;
import view.Validaciones.SoloLetras;
import view.Validaciones.Texto;


public class GestionCelular {

    CelularDao dao = new CelularDao();
    MarcaDao daoM = new MarcaDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();
    Texto texto = new Texto();
    SoloLetras letras = new SoloLetras();
    Entero entero = new Entero();
    EnteroRango rango = new EnteroRango();
    DecimalPositivo decimal = new DecimalPositivo();
    Confirmacion confirmacion = new Confirmacion();

  
        private Marcaa cargarMarca(String mensaje) {
        while (true) {
            var marcas = daoM.obtenerTodos();
            for (Marcaa m : marcas) {
                System.out.println(m.getId() + ". " + m.getNombre());
            }
            System.out.println("0. Crear una marca nueva");
            int id = entero.validarEntero(mensaje);
            if (id == 0) {
                Marcaa nueva = new Marcaa(0, letras.validarSoloLetras("Ingrese el nombre de la nueva marca"));
                if (daoM.insertarMarca(nueva)) {
                    return nueva;
                }
                System.out.println("No se pudo crear la marca (¿ya existe?).");
            } else {
                Marcaa marca = daoM.obtenerPorId(id);
                if (marca != null) {
                    return marca;
                }
                System.out.println("No existe una marca con ese ID.");
            }
        }
    }

    private Celularr.SistemaOperativo escogerSistema() {
        int op = rango.validarEnteroRango("Sistema operativo: 1. IOS  2. ANDROID", 1, 2);
        return Celularr.SistemaOperativo.values()[op - 1];
    }

    private Celularr.Gama escogerGama() {
        int op = rango.validarEnteroRango("Gama: 1. BAJA  2. MEDIA  3. ALTA", 1, 3);
        return Celularr.Gama.values()[op - 1];
    }

   private void agregar() {
    Marcaa marca = cargarMarca("Escoja el id de la marca");
    String modelo = texto.validarTexto("Ingrese el modelo");
    double precio = decimal.validarDecimalPositivo("Ingrese el precio");
    int stock = rango.validarEnteroRango("Ingrese el stock", 1, 10000);
    Celularr.SistemaOperativo so = escogerSistema();
    Celularr.Gama gama = escogerGama();
    dao.insertar(new Celularr(0, stock, modelo, precio, marca, so, gama));
}

    private void listar() {
        var lista = dao.obtenerTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay celulares registrados.");
        }
        for (Celularr cel : lista) {
            System.out.println(cel);
        }
    }

    private void menuActualizar(Celularr cel) {
        boolean cancelar = false;
        int op = menuAdmin.escogerActualizarCelular();
        switch (op) {
            case 1 -> cel.setModelo(texto.validarTexto("Ingrese el nuevo modelo"));
            case 2 -> cel.setStock(rango.validarEnteroRango("Ingrese el nuevo stock", 0, 10000));
            case 3 -> cel.setMarca(cargarMarca("Ingrese el id de la nueva marca"));
            case 4 -> cel.setSistemaOperativo(escogerSistema());
            case 5 -> cel.setGama(escogerGama());
            case 6 -> cel.setPrecio(decimal.validarDecimalPositivo("Ingrese el nuevo precio"));
            case 7 -> {
                System.out.println("Operacion cancelada");
                cancelar = true;
            }
            default -> {
                System.out.println("Opcion no encontrada!");
                cancelar = true;
            }
        }
        if (cancelar == false) {
            dao.actualizar(cel);
        }
    }

    private void actualizar() {
        listar();
        int id = entero.validarEntero("Ingrese el id a actualizar");
        Celularr cel = dao.obtenerPorId(id);
        if (cel == null) {
            System.out.println("No existe dicha entidad");
        } else {
            menuActualizar(cel);
        }
    }

    private void eliminar() {
        listar();
        int id = entero.validarEntero("Ingrese el id a eliminar");
        if (confirmacion.validarConfirmacion("¿Desea eliminar el celular?")) {
            dao.eliminar(id);
        } else {
            System.out.println("Operacion cancelada correctamente!");
        }
    }

    public void menu() {
        int op;
        do {
            op = menuAdmin.escogerGestionarCelular();
            switch (op) {
                case 1 -> agregar();
                case 2 -> actualizar();
                case 3 -> listar();
                case 4 -> eliminar();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 5);
    }
}
