package Pinterest.Usuario;

public class UsuarioVerificado extends Usuario {

    private String categoria;

    public UsuarioVerificado(String id, String nombre, String email, String categoria) {
        super(id, nombre, email);
        this.categoria = categoria;
        this.perfilCompleto = true;
    }

    @Override
    public boolean puedePublicar() {
        return true;
    }

    public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return "[Verificado ✓ - " + categoria + "] " + super.toString();
    }
}
