package Pinterest.Contenido;

import Pinterest.Enum.EstadoReporte;
import Pinterest.Enum.TipoReporte;

import java.util.Objects;

public class Reporte {
    private int id;
    private TipoReporte motivo;
    private String justificacion;
    private EstadoReporte estado;

    public Reporte(int id, TipoReporte motivo, String justificacion) {
        this.motivo = motivo;
        this.justificacion = justificacion;
        this.id = id;
        this.estado = EstadoReporte.PENDIENTE;
    }

    public void setEstado(EstadoReporte estado) {this.estado = estado;}
    public EstadoReporte getEstado() {return estado;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reporte reporte = (Reporte) o;
        return id == reporte.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", motivo=" + motivo +
                ", justificacion='" + justificacion + '\'' +
                ", estado=" + estado +
                '}';
    }
}

