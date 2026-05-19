package Pinterest.Pins;

import Pinterest.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class PinIdea extends Pin {
    private List<String> pasos;
    public PinIdea(int id, String titulo, Usuario usuario, String categoria,
                   String urlImagen) {
        super(id, titulo, usuario, categoria, urlImagen, "Pin de idea");
        this.pasos = new ArrayList<>();
    }

    public void agregarPaso(String paso) {
        if (paso != null && !paso.isBlank()) {
            pasos.add(paso.trim());
        }
    }

    public void eliminarPaso(int indice) {
        if (indice >= 0 && indice < pasos.size()) {
            pasos.remove(indice);
        }
    }

    public List<String> getPasos() { return pasos; }

    @Override
    public String toString() {
        return "PinIdea{" +
                "id =" + id +
                ", Titulo ='" + titulo + '\'' +
                ", Categoria ='" + categoria + '\'' +
                ", Pasos =" + pasos +
                ", Es Publico =" + esPublico +
                '}';
    }
}