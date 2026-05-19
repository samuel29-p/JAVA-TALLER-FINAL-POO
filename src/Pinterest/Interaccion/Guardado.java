package Pinterest.Interaccion;

import Pinterest.Contenido.Contenido;
import Pinterest.Exceptions.PinterestException;
import Pinterest.Tablero.Tablero;
import Pinterest.Usuario.Usuario;

public class Guardado extends Interaccion {
    private Tablero tableroDestino;

    public Guardado(int id, Usuario usuario, Contenido contenido, Tablero tableroDestino) {
        super(id, usuario, contenido);
        if (tableroDestino == null) {
            throw new PinterestException("El tablero destino no puede ser nulo.");
        }
        this.tableroDestino = tableroDestino;
    }

    public Tablero getTableroDestino(){
        return tableroDestino;
    }

    public void setTableroDestino(Tablero tableroDestino) {
        if (tableroDestino == null) {
            throw new PinterestException("El tablero destino no puede ser nulo.");
        }
        this.tableroDestino = tableroDestino;
    }

    @Override
    public void ejecutar() {
        System.out.println(getUsuario() + " guardó '" + getContenido()
                + "' en el tablero '" + tableroDestino.getNombre() + "'");
    }

    @Override
    public String toString() {
        return "Guardado{" +
                "id =" + getId() +
                ", Usuario =" + getUsuario() +
                ", Contenido =" + getContenido() +
                ", Tablero ='" + tableroDestino.getNombre() + '\'' +
                '}';
    }
}
