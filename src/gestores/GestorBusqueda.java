package gestores;

import contenido.*;
import enums.TipoContenido;
import excepciones.ContenidoNoDisponibleException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GestorBusqueda — Persona 4
 * Centraliza todas las búsquedas sobre el catálogo de contenido.
 * Delega en GestorContenido para acceder al catálogo real.
 */
public class GestorBusqueda {

    // ── Singleton ────────────────────────────────────────────────
    private static GestorBusqueda instancia;

    private GestorBusqueda() {}

    public static GestorBusqueda getInstancia() {
        if (instancia == null) {
            instancia = new GestorBusqueda();
        }
        return instancia;
    }

    // ── Dependencia interna ───────────────────────────────────────
    private final GestorContenido gestorContenido = GestorContenido.getInstancia();

    // ── Búsquedas principales ─────────────────────────────────────

    /**
     * Busca contenidos cuyo título o descripción contengan la etiqueta dada
     * (búsqueda case-insensitive).
     *
     * @param etiqueta texto a buscar
     * @return lista de contenidos que coinciden (puede estar vacía)
     */
    public List<Contenido> buscarPorEtiqueta(String etiqueta) {
        if (etiqueta == null || etiqueta.isBlank())
            return Collections.emptyList();

        String termino = etiqueta.toLowerCase().trim();

        return gestorContenido.getTodosLosContenidos().stream()
                .filter(c -> contieneTermino(c, termino))
                .collect(Collectors.toList());
    }

    /**
     * Filtra contenidos por tipo exacto.
     *
     * @param tipo el TipoContenido a filtrar (PIN, VIDEO, IDEA, HISTORIA)
     * @return lista filtrada (puede estar vacía)
     */
    public List<Contenido> filtrarPorTipo(TipoContenido tipo) {
        if (tipo == null) return Collections.emptyList();
        return gestorContenido.filtrarPorTipo(tipo);
    }

    /**
     * Filtra contenidos por rango de fechas de publicación [desde, hasta].
     *
     * @param desde fecha de inicio (inclusive)
     * @param hasta fecha de fin (inclusive)
     * @return lista filtrada
     */
    public List<Contenido> filtrarPorFecha(LocalDate desde, LocalDate hasta) {
        return gestorContenido.filtrarPorFecha(desde, hasta);
    }

    /**
     * Búsqueda combinada: etiqueta + tipo + rango de fechas.
     * Cualquier parámetro puede ser null para ignorar ese filtro.
     *
     * @param etiqueta   texto a buscar (null = sin filtro)
     * @param tipo       tipo de contenido (null = sin filtro)
     * @param desde      fecha inicio (null = sin límite inferior)
     * @param hasta      fecha fin (null = sin límite superior)
     * @return lista de contenidos que cumplen todos los filtros activos
     */
    public List<Contenido> buscarAvanzado(String etiqueta, TipoContenido tipo,
                                          LocalDate desde, LocalDate hasta) {
        return gestorContenido.getTodosLosContenidos().stream()
                .filter(c -> etiqueta == null || etiqueta.isBlank()
                        || contieneTermino(c, etiqueta.toLowerCase().trim()))
                .filter(c -> tipo == null || c.getTipo() == tipo)
                .filter(c -> desde == null
                        || !c.getFechaPublicacion().isBefore(desde))
                .filter(c -> hasta == null
                        || !c.getFechaPublicacion().isAfter(hasta))
                .collect(Collectors.toList());
    }

    /**
     * Busca contenidos de un autor específico por su ID.
     *
     * @param idAutor ID del usuario autor
     * @return lista de contenidos del autor
     */
    public List<Contenido> buscarPorAutor(String idAutor) {
        if (idAutor == null || idAutor.isBlank()) return Collections.emptyList();
        return gestorContenido.getTodosLosContenidos().stream()
                .filter(c -> c.getAutor() != null
                        && c.getAutor().getId().equals(idAutor))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el contenido más popular disponible.
     *
     * @throws ContenidoNoDisponibleException si no hay contenidos
     */
    public Contenido getContenidoMasPopular() throws ContenidoNoDisponibleException {
        return gestorContenido.getContenidoMasPopular();
    }

    // ── Utilidades privadas ───────────────────────────────────────

    /**
     * Comprueba si un contenido contiene el término en título o descripción.
     */
    private boolean contieneTermino(Contenido c, String termino) {
        String titulo = c.getTitulo() != null ? c.getTitulo().toLowerCase() : "";
        String desc   = c.getDescripcion() != null ? c.getDescripcion().toLowerCase() : "";
        return titulo.contains(termino) || desc.contains(termino);
    }
}
