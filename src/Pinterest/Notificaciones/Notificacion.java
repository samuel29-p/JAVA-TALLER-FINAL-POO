package Pinterest.Notificaciones;

import Pinterest.Enum.TipoNotificacion;

import java.time.LocalDateTime;

public class Notificacion {
    private String id;
    private String mensaje;
    private TipoNotificacion tipo;
    private LocalDateTime fecha;
    private boolean leida;

    public Notificacion(String id, String mensaje, TipoNotificacion tipo){
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    public void marcarleida(){
        this.leida = true;
    }

    public String getId() {return id;}
    public String getMensaje() {return mensaje;}
    public TipoNotificacion getTipo() {return tipo;}
    public LocalDateTime getFecha() {return fecha;}
    public boolean isLeida() {return leida;}

    @Override
    public String toString() {
        return "Pinterest.Notificaciones.Notificacion{" +
                "id='" + id + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", leida=" + leida +
                '}';
    }
}
