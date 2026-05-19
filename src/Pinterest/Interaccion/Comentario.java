package Pinterest.Interaccion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comentario extends Interaccion {
    private String texto;
    private Comentario comentarioPadre;
    private List<Comentario> respuestas;

    public Comentario(int id, Usuario usuario, Contenido contenido, String texto) {
        super(id, usuario, contenido);
        this.texto = texto;
        this.comentarioPadre = null;
        this.respuestas = new ArrayList<>();
    }

    public Comentario(int id, Usuario usuario, Contenido contenido,
                      String texto, Comentario comentarioPadre) {
        this(id, usuario, contenido, texto);
        this.comentarioPadre = comentarioPadre;
        comentarioPadre.agregarRespuesta(this);
    }

    public void agregarRespuesta(Comentario respuesta) {
        if (respuesta != null && !respuestas.contains(respuesta)) {
            respuestas.add(respuesta);
        }
    }

    public void eliminarRespuesta(Comentario respuesta) {
        respuestas.remove(respuesta);
    }

    public int contarRespuestasTotal() {
        int total = respuestas.size();
        for (Comentario r : respuestas) {
            total += r.contarRespuestasTotal();
        }
        return total;
    }

    public void imprimirArbol(int nivel) {
        String indent = "  ".repeat(nivel);
        System.out.println(indent + "  " + getUsuario() + ": " + texto);
        for (Comentario r : respuestas) {
            r.imprimirArbol(nivel + 1);
        }
    }

    public String getText(){
        return texto;
    }
    public Comentario getComentarioPadre(){
        return comentarioPadre;
    }
    public List<Comentario> getRespuestas(){
        return Collections.unmodifiableList(respuestas);
    }
    public boolean esRespuesta(){
        return comentarioPadre != null;
    }


    public void setTexto(String texto){
        this.texto = texto;
    }

    @Override
    public void ejecutar() {
        System.out.println(getUsuario() + " comentó en " + getContenido() + ": " + texto);
    }

    @Override
    public String toString() {
        String padre = (comentarioPadre != null)
                ? ", Responde a id =" + comentarioPadre.getId()
                : "";
        return "Comentario{" +
                "id =" + getId() +
                ", Usuario =" + getUsuario() +
                ", Texto ='" + texto + '\'' +
                padre +
                ", Respuestas =" + respuestas.size() +
                '}';
    }
}
