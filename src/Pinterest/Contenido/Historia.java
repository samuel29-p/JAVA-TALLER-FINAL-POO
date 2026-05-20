package Pinterest.Contenido;

import Pinterest.Usuario.Usuario;

import java.time.Duration;
import java.time.LocalDateTime;

public class Historia extends Contenido {
    private LocalDateTime fechaExpiracion;
    private String urlMedia;
    private boolean estaActiva;

    public Historia(int id, String titulo, Usuario creador, String categoria, LocalDateTime fechaExpiracion, String urlMedia) {
        super(id, titulo, creador, categoria);
        this.fechaExpiracion = fechaExpiracion;
        this.urlMedia = urlMedia;
        this.estaActiva = true;
    }

    public boolean estaExpirada() {
        return fechaExpiracion.isBefore(LocalDateTime.now());
    }

    public long tiempoRestante() {
        return Duration.between(LocalDateTime.now(), fechaExpiracion).toMinutes();
    }

    @Override
    public String getDescripcion() {
        return "Historia activa: " + estaActiva + " | Expira: " + fechaExpiracion;
    }

    @Override
    public String toString() {
        return "Historia{" + super.toString() +
                "fechaExpiracion=" + fechaExpiracion +
                ", urlMedia='" + urlMedia + '\'' +
                ", estaActiva=" + estaActiva +
                '}';
    }


}
