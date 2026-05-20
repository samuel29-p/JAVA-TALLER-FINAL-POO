package Pinterest.Usuario;

import Pinterest.Interface.Notificable;
import Pinterest.Interface.Observer;
import Pinterest.Notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario implements Notificable, Observer, Comparable<Usuario> {

 
    protected String id;
    protected String nombre;
    protected String email;
    protected String bio;
    protected boolean perfilCompleto;
    protected int totalPublicaciones;
    protected int totalInteracciones;

    protected List<String> seguidos;

  
    protected List<Notificacion> notificaciones;

   
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.perfilCompleto = false;
        this.totalPublicaciones = 0;
        this.totalInteracciones = 0;
        this.seguidos = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

  
    public void completarPerfil(String bio) {
        this.bio = bio;
        this.perfilCompleto = true;
    }

   
    public void seguir(Usuario otro) {
        if (!seguidos.contains(otro.getId())) {
            seguidos.add(otro.getId());
        }
    }


    public void dejarDeSeguir(Usuario otro) {
        seguidos.remove(otro.getId());
    }

    
    public boolean sigueA(String otroId) {
        return seguidos.contains(otroId);
    }

    
    public int getCantidadSeguidos() {
        return seguidos.size();
    }

   

    @Override
    public void recibirNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    @Override
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }


    @Override
    public void actualizar(Notificacion notificacion) {
        recibirNotificacion(notificacion);
    }

    @Override
    public int compareTo(Usuario otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    
    public void incrementarPublicaciones() {
        totalPublicaciones++;
    }

   
    public void incrementarInteracciones() {
        totalInteracciones++;
    }


    public abstract boolean puedePublicar();

    
    public String getId()                { return id; }
    public String getNombre()            { return nombre; }
    public String getEmail()             { return email; }
    public String getBio()               { return bio; }
    public boolean isPerfilCompleto()    { return perfilCompleto; }
    public int getTotalPublicaciones()   { return totalPublicaciones; }
    public int getTotalInteracciones()   { return totalInteracciones; }
    public List<String> getSeguidos()    { return seguidos; }

    // toString: cómo se ve el usuario cuando lo imprimes en consola
    @Override
    public String toString() {
        return "Usuario | id: " + id + " | nombre: " + nombre + " | email: " + email;
    }
}
