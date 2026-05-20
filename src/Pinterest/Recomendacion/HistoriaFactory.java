package Pinterest.Recomendacion;

import Pinterest.Contenido.Historia;
import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;
import java.time.LocalDateTime;

public class HistoriaFactory extends ContenidoFactory {

    @Override
    public Contenido crear(int id, String titulo, Usuario autor, String categoria) {
        return new Historia(id, titulo, autor, categoria, LocalDateTime.now().plusHours(24), "url-default");
    }
}
