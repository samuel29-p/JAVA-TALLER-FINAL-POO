package Pinterest.Pins;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Pin extends Contenido {
    private String urlImagen;
    private String descripcion;

    public Pin(int id, String titulo, Usuario usuario, String categoria,
               String urlImagen, String descripcion) {
        super(id, titulo, usuario, categoria);
        this.urlImagen = urlImagen;
        this.descripcion = descripcion;
    }

    public String getUrlImagen(){
        return urlImagen;
    }
    public String getDescripcion(){
        return descripcion;
    }

    public void setUrlImagen(String urlImagen){
        this.urlImagen = urlImagen;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    @Override
    public int compareTo(Contenido otro) {
        return otro.getFechaCreacion().compareTo(this.fechaCreacion);
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id =" + id +
                ", Titulo ='" + titulo + '\'' +
                ", Categoria ='" + categoria + '\'' +
                ", Imagen url ='" + urlImagen + '\'' +
                ", Descripcion ='" + descripcion + '\'' +
                ", Es Publico =" + esPublico +
                '}';
    }
}