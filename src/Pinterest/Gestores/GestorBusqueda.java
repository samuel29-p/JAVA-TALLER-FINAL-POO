package Pinterest.Gestores;

import Pinterest.Contenido.Contenido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GestorBusqueda {

    private static GestorBusqueda instancia;
    private final GestorContenido gestorContenido;

    private GestorBusqueda() {
        this.gestorContenido = GestorContenido.getInstancia();
    }

    public static GestorBusqueda getInstancia() {
        if (instancia == null) {
            instancia = new GestorBusqueda();
        }
        return instancia;
    }

    public List<Contenido> buscarPorEtiqueta(String etiqueta) {
        return gestorContenido.getTodos().stream()
                .filter(c -> c.etiquetas != null && c.etiquetas.stream()
                        .anyMatch(e -> e.equalsIgnoreCase(etiqueta)))
                .collect(Collectors.toList());
    }

    public List<Contenido> buscarPorTitulo(String titulo) {
        return gestorContenido.getTodos().stream()
                .filter(c -> c.titulo != null && c.titulo.toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Contenido> filtrarPorCategoria(String categoria) {
        return gestorContenido.filtrarPorCategoria(categoria);
    }

    public List<Contenido> filtrarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return gestorContenido.filtrarPorFecha(desde, hasta);
    }

    public List<Contenido> buscarAvanzado(String etiqueta, String categoria, LocalDateTime desde, LocalDateTime hasta) {
        return gestorContenido.getTodos().stream()
                .filter(c -> etiqueta == null || (c.etiquetas != null && c.etiquetas.stream()
                        .anyMatch(e -> e.equalsIgnoreCase(etiqueta))))
                .filter(c -> categoria == null || (c.categoria != null && c.categoria.equalsIgnoreCase(categoria)))
                .filter(c -> desde == null || !c.fechaCreacion.isBefore(desde))
                .filter(c -> hasta == null || !c.fechaCreacion.isAfter(hasta))
                .collect(Collectors.toList());
    }

    public List<Contenido> buscarPorAutor(String nombreAutor) {
        return gestorContenido.getTodos().stream()
                .filter(c -> c.usuario != null && c.usuario.getNombre().equalsIgnoreCase(nombreAutor))
                .collect(Collectors.toList());
    }
}
