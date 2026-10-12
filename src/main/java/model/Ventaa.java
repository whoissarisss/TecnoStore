package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ventaa {

   
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

    public double getTotal() {
        return detalles.stream().mapToDouble(DetalleVentaa::getSubtotal).sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Clientee getCliente() { return cliente; }
    public LocalDateTime getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public List<DetalleVentaa> getDetalles() { return detalles; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=========================================\n");
        sb.append(String.format(" Venta #%d  [%s]%n", id, estado));
        sb.append(String.format(" Cliente: %s %s%n", cliente.getNombre(), cliente.getApellido()));
        sb.append(String.format(" Fecha:   %s%n",
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        sb.append("-----------------------------------------\n");
        for (DetalleVentaa d : detalles) {
            sb.append(d).append("\n");
        }
        sb.append("-----------------------------------------\n");
        sb.append(String.format(" TOTAL: $%,.2f%n", getTotal()));
        sb.append("=========================================");
        return sb.toString();
    }
}