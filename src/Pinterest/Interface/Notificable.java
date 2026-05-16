package Pinterest.Interface;

import Pinterest.Notificaciones.Notificacion;

import java.util.List;

public interface Notificable {
    void recibirNotificacion(Notificacion notificacion);
    List<Notificacion> getNotificaciones();
}
