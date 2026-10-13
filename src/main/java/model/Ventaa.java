package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import view.Marco;

public class Ventaa {

    public static final double IVA = 0.19;

    public enum Estado {
        PENDIENTE, PAGADA, CANCELADA
    }

    private int id;
    private Clientee cliente;
    private LocalDateTime fecha;
    private Estado estado;
    private List<DetalleVentaa> detalles = new ArrayList<>();

   
    public Ventaa(Clientee cliente) {
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.estado = Estado.PENDIENTE;
    }

   
    public Ventaa(int id, Clientee cliente, LocalDateTime fecha, Estado estado) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
    }

    public void agregarDetalle(DetalleVentaa detalle) {
        detalles.add(detalle);
    }

    public double getSubtotal() {
        return redondear(detalles.stream().mapToDouble(DetalleVentaa::getSubtotal).sum());
    }

    public double getIva() {
        return redondear(getSubtotal() * IVA);
    }

    
    public double getTotal() {
        return redondear(getSubtotal() + getIva());
    }

    private static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Clientee getCliente() {
        return cliente;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public Estado getEstado() { 
        return estado; 
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public List<DetalleVentaa> getDetalles() {
        return detalles;
    }

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Marco.encabezado("VENTA #" + id));
    sb.append(Marco.vacia());
    sb.append(Marco.linea("   * Estado  : " + estado));
    sb.append(Marco.linea("   * Cliente : " + cliente.getNombre() + " " + cliente.getApellido()));
    sb.append(Marco.linea("   * Fecha   : "
            + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
    sb.append(Marco.vacia());
    sb.append(Marco.separador());
    for (DetalleVentaa d : detalles) {
        sb.append(Marco.par(d.getCantidad() + "x " + d.getCelular().getModelo(),
                String.format("$%,.2f", d.getSubtotal())));
    }
    sb.append(Marco.separador());
    sb.append(Marco.par("Subtotal", String.format("$%,.2f", getSubtotal())));
    sb.append(Marco.par("IVA (19%)", String.format("$%,.2f", getIva())));
    sb.append(Marco.par("TOTAL", String.format("$%,.2f", getTotal())));
    sb.append(Marco.cierre());
    return sb.toString();
}
}