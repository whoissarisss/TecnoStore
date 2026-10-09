package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ventaa {
   private int idVenta;
   private Clientee cliente;
   private LocalDateTime fecha;
   private Estado estado;
   private double subtotal;
   private double total;
   
   private List<DetalleVentaa> detalles = new ArrayList<>();

    public Ventaa(int idVenta, Clientee cliente, LocalDateTime fecha, Estado estado, double subtotal, double total) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.subtotal = subtotal;
        this.total = total;
    }

    public Ventaa(Clientee cliente, LocalDateTime fecha, Estado estado, double subtotal, double total) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.subtotal = subtotal;
        this.total = total;
    }

   public enum Estado{
       PENDIENTE, ENVIADO, CANCELADO
   }
   
   public void agregarDetalle(DetalleVentaa detalle){
        detalles.add(detalle);
   }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Clientee getCliente() {
        return cliente;
    }

    public void setCliente(Clientee cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVentaa> getDetalles() {
        return detalles;
    }
   
}
