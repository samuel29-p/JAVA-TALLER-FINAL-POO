package Pinterest.Usuario;

public class Moderador extends Usuario {

    private String zona;

    public Moderador(String id, String nombre, String email, String zona) {
        super(id, nombre, email);
        this.zona = zona;
        this.perfilCompleto = true;
    }

    @Override
    public boolean puedePublicar() {
        return false;
    }

    public void resolverReporte(String idReporte) {
        System.out.println("Moderador " + nombre + " resolvió el reporte: " + idReporte);
    }

    public String getZona() { return zona; }

    @Override
    public String toString() {
        return "[Moderador - " + zona + "] " + super.toString();
    }
}
