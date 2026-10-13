package model;

public class DetalleVentaa {

    private int id;
    private Celularr celular;
    private int cantidad;
    private double subtotal;


    public DetalleVentaa(Celularr celular, int cantidad) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = celular.getPrecio() * cantidad;
    }

    public DetalleVentaa(int id, Celularr celular, int cantidad, double subtotal) {
        this.id = id;
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
    
    public int getCantidad() {
        return cantidad;
    }
    
    public double getSubtotal() {
        return subtotal; 
    }

    @Override
    public String toString() {
        return String.format(" [%d] %dx %s %s ... $%,.2f", id, cantidad,
                celular.getMarca().getNombre(), celular.getModelo(), subtotal);
    }
}