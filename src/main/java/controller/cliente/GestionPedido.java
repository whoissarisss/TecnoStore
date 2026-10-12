package controller.cliente;

import dao.CelularDao;
import dao.DetalleVentaDao;
import dao.VentaDao;
import model.Celularr;
import model.Clientee;
import model.DetalleVentaa;
import model.Ventaa;
import view.MenuCliente;
import view.Validaciones.Confirmacion;
import view.Validaciones.Entero;
import view.Validaciones.EnteroRango;

public class GestionPedido {

    private final Clientee cliente;
    VentaDao dao = new VentaDao();
    DetalleVentaDao daoD = new DetalleVentaDao();
    CelularDao daoC = new CelularDao();
    MenuCliente menuCliente = new MenuCliente();
    Entero entero = new Entero();
    EnteroRango rango = new EnteroRango();
    Confirmacion confirmacion = new Confirmacion();

    public GestionPedido(Clientee cliente) {
        this.cliente = cliente;
    }

    // Muestra solo los celulares con stock. Devuelve false si no hay ninguno.
    private boolean mostrarCatalogo() {
        boolean hay = false;
        for (Celularr cel : daoC.obtenerCelulares()) {
            if (cel.getStock() > 0) {
                System.out.println(String.format("%d. %s %s | %s | %s | $%,.2f | quedan %d",
                        cel.getId(), cel.getMarca().getNombre(), cel.getModelo(),
                        cel.getSistemaOperativo(), cel.getGama(), cel.getPrecio(), cel.getStock()));
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay celulares disponibles por el momento.");
        }
        return hay;
    }

    private int cantidadEnPedido(Ventaa venta, int idCelular) {
        int total = 0;
        for (DetalleVentaa d : venta.getDetalles()) {
            if (d.getCelular().getId() == idCelular) {
                total += d.getCantidad();
            }
        }
        return total;
    }

    // Pide celular y cantidad, descontando lo que ya hay en el pedido. Devuelve null si no se pudo.
    private DetalleVentaa pedirDetalle(Ventaa venta) {
        if (!mostrarCatalogo()) {
            return null;
        }
        Celularr cel = daoC.buscar(entero.validarEntero("Ingrese el id del celular"));
        if (cel == null) {
            System.out.println("No existe dicho celular");
            return null;
        }
        int disponible = cel.getStock() - cantidadEnPedido(venta, cel.getId());
        if (disponible <= 0) {
            System.out.println("No hay mas unidades disponibles de ese celular");
            return null;
        }
        int cantidad = rango.validarEnteroRango("Ingrese la cantidad", 1, disponible);
        return new DetalleVentaa(cel, cantidad);
    }

    private void crear() {
        Ventaa venta = new Ventaa(cliente);
        boolean otro;
        do {
            DetalleVentaa d = pedirDetalle(venta);
            if (d != null) {
                venta.agregarDetalle(d);
                System.out.println("Agregado: " + d.getCantidad() + "x " + d.getCelular().getModelo());
            }
            otro = confirmacion.validarConfirmacion("¿Desea agregar otro celular?");
        } while (otro);

        if (venta.getDetalles().isEmpty()) {
            System.out.println("Pedido cancelado, no agrego ningun celular");
            return;
        }
        System.out.println(venta);
        if (confirmacion.validarConfirmacion("¿Desea confirmar el pedido?")) {
            dao.crear(venta);
            System.out.println("Su pedido queda PENDIENTE hasta que el administrador confirme el pago.");
        } else {
            System.out.println("Pedido descartado");
        }
    }

    private void ver() {
        var lista = dao.listarPorCliente(cliente.getId());
        if (lista.isEmpty()) {
            System.out.println("No tiene pedidos registrados.");
        }
        for (Ventaa v : lista) {
            System.out.println(v);
        }
    }

    // Muestra los pedidos del cliente y devuelve el que escoja (o null)
    private Ventaa escogerPedido(String mensaje, boolean soloPendientes) {
        boolean hay = false;
        for (Ventaa v : dao.listarPorCliente(cliente.getId())) {
            if (!soloPendientes || v.getEstado() == Ventaa.Estado.PENDIENTE) {
                System.out.println(v);
                hay = true;
            }
        }
        if (!hay) {
            System.out.println(soloPendientes ? "No tiene pedidos pendientes." : "No tiene pedidos registrados.");
            return null;
        }
        Ventaa venta = dao.buscar(entero.validarEntero(mensaje));
        if (venta == null || venta.getCliente().getId() != cliente.getId()) {
            System.out.println("No existe dicho pedido");
            return null;
        }
        if (soloPendientes && venta.getEstado() != Ventaa.Estado.PENDIENTE) {
            System.out.println("Solo puede modificar pedidos pendientes");
            return null;
        }
        return venta;
    }

    private void actualizar() {
        Ventaa venta = escogerPedido("Ingrese el id del pedido a actualizar", true);
        if (venta == null) {
            return;
        }
        int op = menuCliente.OpcionActualizarPedido();
        switch (op) {
            case 1 -> {
                DetalleVentaa d = pedirDetalle(venta);
                if (d != null) {
                    daoD.agregar(venta.getId(), d);
                }
            }
            case 2 -> {
                if (venta.getDetalles().size() == 1) {
                    System.out.println("El pedido quedaria vacio, use la opcion Cancelar pedido");
                } else {
                    int idDetalle = entero.validarEntero("Ingrese el numero entre corchetes [ ] del celular a quitar");
                    daoD.eliminar(venta.getId(), idDetalle);
                }
            }
            case 3 -> {
                if (confirmacion.validarConfirmacion("¿Desea cancelar el pedido?")) {
                    dao.actualizarEstado(venta.getId(), Ventaa.Estado.CANCELADA);
                } else {
                    System.out.println("Operacion cancelada correctamente!");
                }
            }
            case 4 -> System.out.println("Operacion cancelada");
            default -> System.out.println("Opcion no encontrada!");
        }
    }

    private void eliminar() {
        Ventaa venta = escogerPedido("Ingrese el id del pedido a eliminar", false);
        if (venta == null) {
            return;
        }
        if (confirmacion.validarConfirmacion("¿Desea eliminar el pedido?")) {
            dao.eliminar(venta.getId());
        } else {
            System.out.println("Operacion cancelada correctamente!");
        }
    }

    public void menu() {
        int op;
        do {
            op = menuCliente.opcionGestionPedido();
            switch (op) {
                case 1 -> crear();
                case 2 -> actualizar();
                case 3 -> ver();
                case 4 -> eliminar();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 5);
    }
}

