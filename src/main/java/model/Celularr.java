package model;

public class Celularr {
   private int id;
   private int stock;
   private String modelo;
   private double precio;
   private Marcaa marca;
   private SistemaOperativo sistemaOperativo;
   private Gama gama; 
   
    public enum SistemaOperativo {
        IOS, ANDROID
    }

    public enum Gama {
        BAJA, MEDIA, ALTA
    }

    public Celularr(int id, int stock, String modelo, double precio, Marcaa marca, SistemaOperativo sistemaOperativo, Gama gama) {
        this.id = id;
        this.stock = stock;
        this.modelo = modelo;
        this.precio = precio;
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
        this.gama = gama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Marcaa getMarca() {
        return marca;
    }

    public void setMarca(Marcaa marca) {
        this.marca = marca;
    }

    public SistemaOperativo getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

}
