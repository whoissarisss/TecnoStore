package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Auditoria {

    private final int id;
    private final LocalDateTime fecha;
    private final String usuario;
    private final String accion;
    private final String entidad;
    private final Integer idEntidad; 
    private final String detalle;

    public Auditoria(int id, LocalDateTime fecha, String usuario, String accion,
                      String entidad, Integer idEntidad, String detalle) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.accion = accion;
        this.entidad = entidad;
        this.idEntidad = idEntidad;
        this.detalle = detalle;
    }

    public int getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public String getUsuario() { return usuario; }
    public String getAccion() { return accion; }
    public String getEntidad() { return entidad; }
    public Integer getIdEntidad() { return idEntidad; }
    public String getDetalle() { return detalle; }

    @Override
    public String toString() {
        return String.format("%s | %-8s | %-12s | %s%s %s",
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                usuario, accion, entidad,
                idEntidad == null ? "" : " #" + idEntidad,
                detalle == null ? "" : "(" + detalle + ")");
    }
}

