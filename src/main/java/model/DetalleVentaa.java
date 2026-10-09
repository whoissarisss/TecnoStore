package model;

public class DetalleVentaa {
    private int id;
    private Celularr celular;
    private int cantidad;
    private double subtotal;

    public DetalleVentaa(int id, Celularr celular, int cantidad, double subtotal) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleVentaa(Celularr celular, int cantidad, double subtotal) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Celularr getCelular() {
        return celular;
    }

    public void setCelular(Celularr celular) {
        this.celular = celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
}
