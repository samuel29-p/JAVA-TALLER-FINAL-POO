package Pinterest.Interaccion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

public class Like extends Interaccion {
    public Like(int id, Usuario usuario, Contenido contenido) {
        super(id, usuario, contenido);
    }

    @Override
    public void ejecutar() {
        System.out.println(getUsuario() + " le dio like a: " + getContenido());
    }

    @Override
    public String toString() {
        return "Like{" +
                "id =" + getId() +
                ", Usuario =" + getUsuario() +
                ", Contenido =" + getContenido() +
                '}';
    }
}

