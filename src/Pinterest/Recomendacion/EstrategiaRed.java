package Pinterest.Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.GestorUsuarios;
import Pinterest.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class EstrategiaRed implements RecomendacionStrategy {

    private GestorUsuarios gestorUsuarios;
    private List<Contenido> catalogoContenido;

    public EstrategiaRed(GestorUsuarios gestorUsuarios, List<Contenido> catalogoContenido) {
        this.gestorUsuarios = gestorUsuarios;
        this.catalogoContenido = catalogoContenido;
    }

    @Override
    public List<Contenido> recomendar(Usuario usuario) {
        List<Contenido> recomendaciones = new ArrayList<>();
        List<String> seguidos = usuario.getSeguidos();

        for (Contenido contenido : catalogoContenido) {
            String autorId = contenido.getUsuario().getId();
            if (seguidos.contains(autorId)) {
                recomendaciones.add(contenido);
            }
        }
        return recomendaciones;
    }
}
