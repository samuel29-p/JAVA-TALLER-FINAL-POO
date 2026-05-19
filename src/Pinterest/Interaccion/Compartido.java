package Pinterest.Interaccion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

public class Compartido extends Interaccion {
    private String destinatario;

    public Compartido(int id, Usuario usuario, Contenido contenido, String destinatario) {
        super(id, usuario, contenido);
        this.destinatario = (destinatario != null && !destinatario.isBlank())
                ? destinatario
                : "externo";
    }

    public String getDestinatario(){
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public void ejecutar() {
        System.out.println(getUsuario() + " compartio '"
                + getContenido() + "' con: " + destinatario);
    }

    @Override
    public String toString() {
        return "Compartido{" +
                "id =" + getId() +
                ", Usuario =" + getUsuario() +
                ", Contenido =" + getContenido() +
                ", Destinatario ='" + destinatario + '\'' +
                '}';
    }
}