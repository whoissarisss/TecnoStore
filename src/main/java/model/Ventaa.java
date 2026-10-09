package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ventaa {
   private int id;
   private Clientee cliente;
   private LocalDateTime fecha;
   private List<DetalleVentaa> detalles = new ArrayList<>();

    public Ventaa(int id, Clientee cliente, LocalDateTime fecha) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Ventaa(Clientee cliente) {
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
    }
   
   public void agregarDetalle(DetalleVentaa detalle){
        detalles.add(detalle);
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

    public List<DetalleVentaa> getDetalles() {
        return detalles;
    }
   
}
