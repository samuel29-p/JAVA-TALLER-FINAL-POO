package Pinterest.Notificaciones;

import Pinterest.Interface.Observable;
import Pinterest.Interface.Observer;
import java.util.ArrayList;
import java.util.List;          // ← este faltaba

public class GestorNotificaciones implements Observable {
    private List<Observer> observers;

    public GestorNotificaciones() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void agregarObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void eliminarObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(Notificacion notificacion){
        observers.forEach(observer -> observer.actualizar(notificacion));
    }

}
