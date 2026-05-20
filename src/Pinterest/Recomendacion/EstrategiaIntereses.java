package Pinterest.Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.GestorUsuarios;
import Pinterest.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class EstrategiaIntereses implements RecomendacionStrategy {
    private GestorUsuarios gestorUsuarios;
    private List<Contenido> catalogoContenido;

    public EstrategiaIntereses(GestorUsuarios gestorUsuarios, List<Contenido> catalogoContenido) {
        this.gestorUsuarios = gestorUsuarios;
        this.catalogoContenido = catalogoContenido;
    }

    @Override
    public List<Contenido> recomendar(Usuario usuario) {
        List<Contenido> recomendaciones = new ArrayList<>();
        double tasaUsuario = gestorUsuarios.getTasaInteraccion(usuario);

        for (Contenido contenido : catalogoContenido) {
            double tasaAutor = gestorUsuarios.getTasaInteraccion(contenido.getUsuario());
            if (tasaAutor >= tasaUsuario && !contenido.getUsuario().getId().equals(usuario.getId())) {
                recomendaciones.add(contenido);
            }
        }
        return recomendaciones;
    }
}
