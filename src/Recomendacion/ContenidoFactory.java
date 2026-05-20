package Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;

public abstract class ContenidoFactory {

    public abstract Contenido crear(int id, String titulo, Usuario autor, String categoria);

}
