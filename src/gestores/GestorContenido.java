package gestores;

import contenido.*;
import usuarios.Usuario;
import interfaces.Exportable;
import excepciones.ContenidoNoDisponibleException;
import enums.TipoContenido;
import estadisticas.Estadisticas;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GestorContenido — Persona 4
 * Administra todo el ciclo de vida del contenido en la plataforma.
 * Implementa Exportable para generar reportes.
 */
public class GestorContenido implements Exportable {

    // ── Singleton ────────────────────────────────────────────────
    private static GestorContenido instancia;

    private GestorContenido() {}

    public static GestorContenido getInstancia() {
        if (instancia == null) {
            instancia = new GestorContenido();
        }
        return instancia;
    }

    // ── Estado interno ────────────────────────────────────────────
    private final List<Contenido> catalogoContenidos = new ArrayList<>();
    private Estadisticas estadisticas = new Estadisticas();

    // ── CRUD básico ───────────────────────────────────────────────

    /**
     * Registra un nuevo contenido en el catálogo.
     */
    public void agregarContenido(Contenido contenido) {
        if (contenido == null) throw new IllegalArgumentException("El contenido no puede ser nulo.");
        catalogoContenidos.add(contenido);
    }

    /**
     * Elimina un contenido por su ID.
     * @throws ContenidoNoDisponibleException si no se encuentra.
     */
    public void eliminarContenido(String id) throws ContenidoNoDisponibleException {
        Contenido encontrado = buscarPorId(id);
        catalogoContenidos.remove(encontrado);
    }

    /**
     * Busca un contenido por ID exacto.
     * @throws ContenidoNoDisponibleException si no existe.
     */
    public Contenido buscarPorId(String id) throws ContenidoNoDisponibleException {
        return catalogoContenidos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ContenidoNoDisponibleException(
                        "Contenido con id '" + id + "' no encontrado."));
    }

    // ── Métodos clave (requeridos por Persona 5) ──────────────────

    /**
     * Filtra contenidos por tipo (PIN, VIDEO, IDEA, HISTORIA).
     */
    public List<Contenido> filtrarPorTipo(TipoContenido tipo) {
        return catalogoContenidos.stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    /**
     * Filtra contenidos cuya fecha de publicación esté en el rango [desde, hasta].
     */
    public List<Contenido> filtrarPorFecha(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
        if (desde.isAfter(hasta)) throw new IllegalArgumentException("'desde' no puede ser posterior a 'hasta'.");

        return catalogoContenidos.stream()
                .filter(c -> {
                    LocalDate fecha = c.getFechaPublicacion();
                    return (fecha.isEqual(desde) || fecha.isAfter(desde))
                        && (fecha.isEqual(hasta) || fecha.isBefore(hasta));
                })
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el contenido con mayor popularidad.
     * Usa calcularPopularidad() definido en Contenido.
     * @throws ContenidoNoDisponibleException si el catálogo está vacío.
     */
    public Contenido getContenidoMasPopular() throws ContenidoNoDisponibleException {
        return catalogoContenidos.stream()
                .max(Comparator.comparingDouble(Contenido::calcularPopularidad))
                .orElseThrow(() -> new ContenidoNoDisponibleException(
                        "No hay contenidos registrados en el catálogo."));
    }

    /**
     * Devuelve los N contenidos más populares ordenados de mayor a menor.
     */
    public List<Contenido> getTopContenidos(int n) throws ContenidoNoDisponibleException {
        if (catalogoContenidos.isEmpty())
            throw new ContenidoNoDisponibleException("No hay contenidos registrados.");
        return catalogoContenidos.stream()
                .sorted(Comparator.comparingDouble(Contenido::calcularPopularidad).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // ── Exportable ────────────────────────────────────────────────

    /**
     * Genera y exporta un reporte completo de contenidos.
     * Implementa Exportable.
     */
    @Override
    public String exportarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       REPORTE DE CONTENIDO\n");
        sb.append("  Generado: ").append(LocalDate.now()).append("\n");
        sb.append("========================================\n\n");

        sb.append("Total de contenidos: ").append(catalogoContenidos.size()).append("\n\n");

        // Resumen por tipo
        sb.append("--- Distribución por tipo ---\n");
        Map<TipoContenido, Long> porTipo = catalogoContenidos.stream()
                .collect(Collectors.groupingBy(Contenido::getTipo, Collectors.counting()));
        porTipo.forEach((tipo, cantidad) ->
                sb.append("  ").append(tipo).append(": ").append(cantidad).append("\n"));

        // Top 5
        sb.append("\n--- Top 5 más populares ---\n");
        catalogoContenidos.stream()
                .sorted(Comparator.comparingDouble(Contenido::calcularPopularidad).reversed())
                .limit(5)
                .forEach(c -> sb.append("  [")
                        .append(String.format("%.2f", c.calcularPopularidad()))
                        .append("] ")
                        .append(c.getTitulo())
                        .append(" (").append(c.getId()).append(")\n"));

        sb.append("\n========================================\n");
        return sb.toString();
    }

    // ── Estadísticas internas ─────────────────────────────────────

    /**
     * Produce un objeto Estadisticas con métricas del contenido gestionado.
     * Requerido por las relaciones del UML: GestorContenido produce Estadisticas.
     */
    public Estadisticas producirEstadisticas() {
        estadisticas.registrarTotalContenidos(catalogoContenidos.size());
        estadisticas.registrarPromedioPopularidad(
                catalogoContenidos.stream()
                        .mapToDouble(Contenido::calcularPopularidad)
                        .average()
                        .orElse(0.0)
        );
        return estadisticas;
    }

    // ── Getters de soporte ────────────────────────────────────────

    public List<Contenido> getTodosLosContenidos() {
        return Collections.unmodifiableList(catalogoContenidos);
    }

    public int getCantidadContenidos() {
        return catalogoContenidos.size();
    }
}
