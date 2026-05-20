package Pinterest.Estadisticas;

import Pinterest.Contenido.Contenido;
import Pinterest.Interface.Exportable;

public class Estadisticas implements Exportable {

    private int totalContenidos;
    private int totalUsuarios;
    private int totalInteracciones;
    private int totalPublicaciones;
    private Contenido contenidoMasReciente;

    public Estadisticas() {
        this.totalContenidos = 0;
        this.totalUsuarios = 0;
        this.totalInteracciones = 0;
        this.totalPublicaciones = 0;
    }

    public void setTotalContenidos(int totalContenidos) { this.totalContenidos = totalContenidos; }
    public void setTotalUsuarios(int totalUsuarios) { this.totalUsuarios = totalUsuarios; }
    public void setTotalInteracciones(int totalInteracciones) { this.totalInteracciones = totalInteracciones; }
    public void setTotalPublicaciones(int totalPublicaciones) { this.totalPublicaciones = totalPublicaciones; }
    public void setContenidoMasReciente(Contenido contenidoMasReciente) { this.contenidoMasReciente = contenidoMasReciente; }

    public int getTotalContenidos() { return totalContenidos; }
    public int getTotalUsuarios() { return totalUsuarios; }
    public int getTotalInteracciones() { return totalInteracciones; }
    public int getTotalPublicaciones() { return totalPublicaciones; }
    public Contenido getContenidoMasReciente() { return contenidoMasReciente; }

    @Override
    public String Exportar() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADISTICAS GENERALES ===\n");
        sb.append("Total usuarios: ").append(totalUsuarios).append("\n");
        sb.append("Total contenidos: ").append(totalContenidos).append("\n");
        sb.append("Total publicaciones: ").append(totalPublicaciones).append("\n");
        sb.append("Total interacciones: ").append(totalInteracciones).append("\n");
        if (contenidoMasReciente != null) {
            sb.append("Contenido mas reciente: ").append(contenidoMasReciente.toString()).append("\n");
        }
        return sb.toString();
    }
}
