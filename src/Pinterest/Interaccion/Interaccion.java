package Pinterest.Interaccion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Interaccion {
    private int id;
    private Usuario usuario;
    private LocalDateTime fecha;
    private Contenido contenido;

    public Interaccion(int id, Usuario usuario, Contenido contenido) {
        this.id = id;
        this.usuario = usuario;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
    }

    public abstract void ejecutar();
    public int getId(){
        return id;
    }
    public Usuario getUsuario(){
        return usuario;
    }
    public Contenido getContenido(){
        return contenido;
    }
    public LocalDateTime getFecha(){
        return fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interaccion that = (Interaccion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Interaccion{" +
                ", usuario=" + usuario +
                ", fecha=" + fecha +
                ", contenido=" + contenido +
                '}';
    }
}


