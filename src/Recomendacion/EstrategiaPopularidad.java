package Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.GestorUsuarios;
import Pinterest.Usuario.Usuario;
import java.util.List;
import java.util.ArrayList;

public class EstrategiaPopularidad implements RecomendacionStrategy {

    private GestorUsuarios gestorUsuarios;
    private List<Contenido> catalogoContenido;

    public EstrategiaPopularidad(GestorUsuarios gestorUsuarios, List<Contenido> catalogoContenido) {
        this.gestorUsuarios = gestorUsuarios;
        this.catalogoContenido = catalogoContenido;
    }

    @Override
    public List<Contenido> recomendar(Usuario usuario) {
        List<Contenido> recomendaciones = new ArrayList<>();
        List<Usuario> influyentes = gestorUsuarios.getMasInfluyentes();

        for (Usuario influyente : influyentes) {
            if (!influyente.getId().equals(usuario.getId())) {
                for (Contenido contenido : catalogoContenido) {
                    if (contenido.getUsuario().getId().equals(influyente.getId())) {
                        recomendaciones.add(contenido);
                    }
                }
            }
        }
        return recomendaciones;
    }
}
