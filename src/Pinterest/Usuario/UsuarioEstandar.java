package Pinterest.Usuario;


public class UsuarioEstandar extends Usuario {

    public UsuarioEstandar(String id, String nombre, String email) {
        super(id, nombre, email);
    }

    @Override
    public boolean puedePublicar() {
        return this.perfilCompleto;
    }

    @Override
    public String toString() {
        return "[Estándar] " + super.toString();
    }
}