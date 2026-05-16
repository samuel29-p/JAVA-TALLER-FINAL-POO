package Pinterest.Interface;

import Pinterest.Notificaciones.Notificacion;

public interface Observable {
    void agregarObserver(Observer observer);
    void eliminarObserver(Observer observer);
    void notificarObservers(Notificacion notificacion);
}
