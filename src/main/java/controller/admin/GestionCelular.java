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
        var marcas = daoM.obtenerMarcas();
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas registradas, vamos a crear la primera.");
            daoM.crear(new Marcaa(0, letras.validarSoloLetras("Ingrese el nombre de la marca")));
        }
        for (Marcaa m : daoM.obtenerMarcas()) {
            System.out.println(m.getId() + ". " + m.getNombre());
        }
        Marcaa marca = daoM.buscar(entero.validarEntero(mensaje));
        while (marca == null) {
            marca = daoM.buscar(entero.validarEntero("Error, intente nuevamente!"));
        }
        return marca;
    }

    // El orden de las opciones debe ser el mismo del enum
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
    dao.crear(new Celularr(0, stock, modelo, precio, marca, so, gama));
}

    private void listar() {
        var lista = dao.obtenerCelulares();
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
        Celularr cel = dao.buscar(id);
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
