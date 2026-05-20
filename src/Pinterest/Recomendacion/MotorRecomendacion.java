package Pinterest.Recomendacion;

import Pinterest.Contenido.Contenido;
import Pinterest.Usuario.Usuario;
import Pinterest.Exceptions.PinterestException;
import java.util.List;

public class MotorRecomendacion {

    private RecomendacionStrategy estrategia;

    public MotorRecomendacion() {
    }

    public void setEstrategia(RecomendacionStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public RecomendacionStrategy getEstrategia() {
        return estrategia;
    }

    public List<Contenido> recomendar(Usuario usuario) {
        if (estrategia == null) {
            throw new PinterestException("No hay ninguna estrategia definida en el motor.");
        }
        return estrategia.recomendar(usuario);
    }
}

