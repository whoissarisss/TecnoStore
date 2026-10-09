package model;

public class DetalleVentaa {
    private int id;
    private Celularr celular;
    private int cantidad;
    private double precio;

    public DetalleVentaa(int id, Celularr celular, int cantidad, double precio) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public DetalleVentaa(Celularr celular, int cantidad, double precio) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.precio = precio;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
