package Pinterest.Pins;

import Pinterest.Usuario.Usuario;

class PinFactory {
    private static int contadorId = 1;
    private PinFactory() {}

    //Crea Pin estandar
    public static Pin crearPin(String titulo, Usuario usuario, String categoria,
                               String urlImagen, String descripcion) {
        return new Pin(contadorId++, titulo, usuario, categoria, urlImagen, descripcion);
    }

    //Crea PinVideo
    public static PinVideo crearPinVideo(String titulo, Usuario usuario, String categoria,
                                         String urlImagen, String urlVideo, int duracionSegundos) {
        return new PinVideo(contadorId++, titulo, usuario, categoria,
                urlImagen, urlVideo, duracionSegundos);
    }

    //Crea PinIdea
    public static PinIdea crearPinIdea(String titulo, Usuario usuario,
                                       String categoria, String urlImagen) {
        return new PinIdea(contadorId++, titulo, usuario, categoria, urlImagen);
    }
}