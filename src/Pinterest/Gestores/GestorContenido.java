package Pinterest.Gestores;

import Pinterest.Contenido.Contenido;
import Pinterest.Exceptions.ContenidoNoDisponibleException;
import Pinterest.Interface.Exportable;
import Pinterest.Estadisticas.Estadisticas;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestorContenido implements Exportable {

    private static GestorContenido instancia;
    private final List<Contenido> contenidos;

    private GestorContenido() {
        this.contenidos = new ArrayList<>();
    }

    public static GestorContenido getInstancia() {
        if (instancia == null) {
            instancia = new GestorContenido();
        }
        return instancia;
    }

    public void agregar(Contenido contenido) {
        contenidos.add(contenido);
    }

    public void eliminar(int id) throws ContenidoNoDisponibleException {
        Contenido encontrado = buscarPorId(id);
        contenidos.remove(encontrado);
    }

    public Contenido buscarPorId(int id) throws ContenidoNoDisponibleException {
        return contenidos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ContenidoNoDisponibleException("Contenido con id " + id + " no encontrado"));
    }

    public List<Contenido> filtrarPorCategoria(String categoria) {
        return contenidos.stream()
                .filter(c -> c.getCategoria() != null && c.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Contenido> filtrarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return contenidos.stream()
                .filter(c -> !c.getFechaCreacion().isBefore(desde) && !c.getFechaCreacion().isAfter(hasta))
                .collect(Collectors.toList());
    }

    public Contenido getContenidoMasReciente() {
        return contenidos.stream()
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    public List<Contenido> getTodos() {
        return Collections.unmodifiableList(contenidos);
    }

    public Estadisticas producirEstadisticas() {
        Estadisticas stats = new Estadisticas();
        stats.setTotalContenidos(contenidos.size());
        stats.setContenidoMasReciente(getContenidoMasReciente());
        return stats;
    }

    @Override
    public String Exportar() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE CONTENIDO ===\n");
        sb.append("Total de contenidos: ").append(contenidos.size()).append("\n");
        for (Contenido c : contenidos) {
            sb.append("- ").append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
