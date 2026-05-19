package Pinterest.Pins;

import Pinterest.Usuario.Usuario;

public class PinVideo extends Pin {
    private String urlVideo;
    private int duracionSegundos;

    public PinVideo(int id, String titulo, Usuario usuario, String categoria,
                    String urlImagen, String urlVideo, int duracionSegundos) {
        super(id, titulo, usuario, categoria, urlImagen, "Pin de video");
        this.urlVideo = urlVideo;
        this.duracionSegundos = duracionSegundos;
    }

    public String getUrlVideo(){
        return urlVideo;
    }
    public int getDuracionSegundos(){
        return duracionSegundos;
    }

    public void setUrlVideo(String urlVideo){
        this.urlVideo = urlVideo;
    }
    public void setDuracionSegundos(int duracionSegundos){
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public String toString() {
        return "PinVideo{" +
                "id =" + id +
                ", titulo ='" + titulo + '\'' +
                ", Video url ='" + urlVideo + '\'' +
                ", Duracion por segundos =" + duracionSegundos +
                ", Es Publico =" + esPublico +
                '}';
    }
}