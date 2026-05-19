package Pinterest.Usuario;

public class Estadisticas {

    private String nombreUsuario;
    private int totalPublicaciones;
    private int totalInteracciones;
    private int totalSeguidores;
    private double tasaInteraccion;

    public Estadisticas(String nombreUsuario, int totalPublicaciones,
                        int totalInteracciones, int totalSeguidores) {
        this.nombreUsuario = nombreUsuario;
        this.totalPublicaciones = totalPublicaciones;
        this.totalInteracciones = totalInteracciones;
        this.totalSeguidores = totalSeguidores;

        this.tasaInteraccion = totalPublicaciones > 0
                ? (double) totalInteracciones / totalPublicaciones
                : 0.0;
    }

    public String getNombreUsuario()   { return nombreUsuario; }
    public int getTotalPublicaciones() { return totalPublicaciones; }
    public int getTotalInteracciones() { return totalInteracciones; }
    public int getTotalSeguidores()    { return totalSeguidores; }
    public double getTasaInteraccion() { return tasaInteraccion; }

    @Override
    public String toString() {
        return "Estadisticas de " + nombreUsuario +
                " | publicaciones: " + totalPublicaciones +
                " | interacciones: " + totalInteracciones +
                " | seguidores: " + totalSeguidores +
                " | tasa: " + tasaInteraccion;
    }
}