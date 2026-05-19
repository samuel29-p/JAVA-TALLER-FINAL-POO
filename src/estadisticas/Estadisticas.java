package estadisticas;

import contenido.Contenido;
import usuarios.Usuario;
import interfaces.Exportable;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Estadisticas — Persona 4 (extiende lo de Persona 2)
 *
 * Persona 2 expone métricas de usuarios (tasas de interacción, influencia).
 * Esta clase las complementa con métricas de contenido y comportamiento global,
 * e implementa Exportable para generar reportes CSV / texto.
 *
 * Relaciones UML:
 *   GestorContenido  --> produce --> Estadisticas
 *   GestorUsuarios   --> produce --> Estadisticas
 */
public class Estadisticas implements Exportable {

    // ── Métricas de contenido (añadidas por Persona 4) ───────────
    private int    totalContenidos;
    private double promedioPopularidad;
    private int    totalInteracciones;
    private Map<String, Integer> interaccionesPorTipo = new LinkedHashMap<>();
    private Map<String, Double>  alcancePorUsuario    = new LinkedHashMap<>();

    // ── Métricas de usuario (base de Persona 2) ──────────────────
    private int    totalUsuarios;
    private double tasaInteraccionGlobal;
    private List<String> masInfluyentes = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────
    public Estadisticas() {}

    // ── Setters / registradores ───────────────────────────────────

    public void registrarTotalContenidos(int total) {
        this.totalContenidos = total;
    }

    public void registrarPromedioPopularidad(double promedio) {
        this.promedioPopularidad = promedio;
    }

    public void registrarTotalInteracciones(int total) {
        this.totalInteracciones = total;
    }

    public void registrarInteraccionesPorTipo(String tipo, int cantidad) {
        interaccionesPorTipo.put(tipo, cantidad);
    }

    public void registrarAlcancePorUsuario(String idUsuario, double alcance) {
        alcancePorUsuario.put(idUsuario, alcance);
    }

    // Métodos que Persona 2 (GestorUsuarios) llama para completar el objeto
    public void registrarTotalUsuarios(int total) {
        this.totalUsuarios = total;
    }

    public void registrarTasaInteraccionGlobal(double tasa) {
        this.tasaInteraccionGlobal = tasa;
    }

    public void registrarMasInfluyentes(List<String> idsInfluyentes) {
        this.masInfluyentes = new ArrayList<>(idsInfluyentes);
    }

    // ── Métodos de consulta (UML: getTasaInteraccion, getAlcance) ─

    /**
     * Tasa de interacción = totalInteracciones / totalContenidos.
     * Retorna 0 si no hay contenidos.
     */
    public double getTasaInteraccion() {
        if (totalContenidos == 0) return 0.0;
        return (double) totalInteracciones / totalContenidos;
    }

    /**
     * Alcance promedio de todos los usuarios registrados.
     */
    public double getAlcancePromedio() {
        if (alcancePorUsuario.isEmpty()) return 0.0;
        return alcancePorUsuario.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Alcance de un usuario específico por su ID.
     * Retorna 0 si no se ha registrado.
     */
    public double getAlcanceDeUsuario(String idUsuario) {
        return alcancePorUsuario.getOrDefault(idUsuario, 0.0);
    }

    // ── Exportable ────────────────────────────────────────────────

    /**
     * Genera un reporte textual completo de las estadísticas.
     */
    @Override
    public String exportarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         REPORTE DE ESTADÍSTICAS\n");
        sb.append("  Generado: ").append(LocalDate.now()).append("\n");
        sb.append("========================================\n\n");

        sb.append("── CONTENIDO ──────────────────────────\n");
        sb.append("  Total contenidos      : ").append(totalContenidos).append("\n");
        sb.append("  Popularidad promedio  : ")
          .append(String.format("%.2f", promedioPopularidad)).append("\n");
        sb.append("  Total interacciones   : ").append(totalInteracciones).append("\n");
        sb.append("  Tasa de interacción   : ")
          .append(String.format("%.4f", getTasaInteraccion())).append("\n");

        if (!interaccionesPorTipo.isEmpty()) {
            sb.append("\n  Interacciones por tipo:\n");
            interaccionesPorTipo.forEach((tipo, cant) ->
                    sb.append("    ").append(tipo).append(": ").append(cant).append("\n"));
        }

        sb.append("\n── USUARIOS ───────────────────────────\n");
        sb.append("  Total usuarios        : ").append(totalUsuarios).append("\n");
        sb.append("  Tasa interacción glob.: ")
          .append(String.format("%.4f", tasaInteraccionGlobal)).append("\n");
        sb.append("  Alcance promedio      : ")
          .append(String.format("%.2f", getAlcancePromedio())).append("\n");

        if (!masInfluyentes.isEmpty()) {
            sb.append("\n  Más influyentes:\n");
            masInfluyentes.forEach(id -> sb.append("    - ").append(id).append("\n"));
        }

        sb.append("\n========================================\n");
        return sb.toString();
    }

    // ── Getters ───────────────────────────────────────────────────

    public int    getTotalContenidos()       { return totalContenidos; }
    public double getPromedioPopularidad()   { return promedioPopularidad; }
    public int    getTotalInteracciones()    { return totalInteracciones; }
    public int    getTotalUsuarios()         { return totalUsuarios; }
    public double getTasaInteraccionGlobal() { return tasaInteraccionGlobal; }
    public List<String> getMasInfluyentes()  { return Collections.unmodifiableList(masInfluyentes); }
    public Map<String, Integer> getInteraccionesPorTipo() {
        return Collections.unmodifiableMap(interaccionesPorTipo);
    }
}
