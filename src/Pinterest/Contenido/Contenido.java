package Pinterest.Contenido;

import Pinterest.Usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Contenido implements Comparable<Contenido>{
    protected int id;
    protected String titulo;
    protected Usuario usuario;
    protected LocalDateTime fechaCreacion;
    protected List<String> etiquetas;
    protected String categoria;
    protected boolean esPublico;
    protected String descripcion;

    public Contenido(int id, String titulo, Usuario usuario, String categoria){
        this.id = id;
        this.titulo = titulo;
        this.usuario = usuario;
        this.categoria = categoria;
        this.etiquetas = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.esPublico = false;
    }

    public abstract String getDescripcion();

    @Override
    public String toString() {
        return "Contenido{" +
                ", titulo='" + titulo + '\'' +
                ", usuario=" + usuario +
                ", fechaCreacion=" + fechaCreacion +
                ", etiquetas=" + etiquetas +
                ", categoria='" + categoria + '\'' +
                ", esPublico=" + esPublico +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contenido contenido = (Contenido) o;
        return id == contenido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Contenido o) {
        return fechaCreacion.compareTo(o.fechaCreacion);
    }
}
