package Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

import java.util.List;

public interface RecomendacionStrategy {
    List<Contenido> recomendar (Usuario usuario);
}
