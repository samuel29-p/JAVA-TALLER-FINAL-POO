package Pinterest.Usuario;

import Pinterest.Interface.Notificable;
import Pinterest.Interface.Observer;
import Pinterest.Notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.List;

// "abstract" significa que esta clase no se puede usar directamente.
// Es solo una plantilla. UsuarioEstandar, UsuarioVerificado y Moderador
// son los que realmente se crean.
public abstract class Usuario implements Notificable, Observer, Comparable<Usuario> {

    // Atributos: la información que tiene todo usuario
    protected String id;
    protected String nombre;
    protected String email;
    protected String bio;
    protected boolean perfilCompleto;
    protected int totalPublicaciones;
    protected int totalInteracciones;

    // Lista de usuarios que este usuario sigue (solo sus ids)
    protected List<String> seguidos;

    // Lista de notificaciones que ha recibido
    protected List<Notificacion> notificaciones;

    // Constructor: lo que se ejecuta cuando se crea un usuario
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

    // Completa el perfil agregando una bio
    public void completarPerfil(String bio) {
        this.bio = bio;
        this.perfilCompleto = true;
    }

    // Seguir a otro usuario: guarda su id en la lista
    public void seguir(Usuario otro) {
        if (!seguidos.contains(otro.getId())) {
            seguidos.add(otro.getId());
        }
    }

    // Dejar de seguir: elimina su id de la lista
    public void dejarDeSeguir(Usuario otro) {
        seguidos.remove(otro.getId());
    }

    // Dice si este usuario sigue a alguien
    public boolean sigueA(String otroId) {
        return seguidos.contains(otroId);
    }

    // Cuántos usuarios sigue
    public int getCantidadSeguidos() {
        return seguidos.size();
    }

    // --- Notificable: métodos para recibir notificaciones ---

    @Override
    public void recibirNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    @Override
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    // --- Observer: cuando ocurre un evento, este método lo recibe ---
    // Por ejemplo: alguien que sigues publicó algo nuevo
    @Override
    public void actualizar(Notificacion notificacion) {
        recibirNotificacion(notificacion);
    }

    // --- Comparable: para poder ordenar usuarios por nombre ---
    // Esto lo usa GestorUsuarios cuando ordena la lista
    @Override
    public int compareTo(Usuario otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    // Para sumar publicaciones desde GestorUsuarios
    public void incrementarPublicaciones() {
        totalPublicaciones++;
    }

    // Para sumar interacciones desde GestorUsuarios
    public void incrementarInteracciones() {
        totalInteracciones++;
    }

    // Método abstracto: cada tipo de usuario lo define distinto
    // "abstract" aquí obliga a que UsuarioEstandar, etc., lo implementen
    public abstract boolean puedePublicar();

    // Getters: métodos para leer los atributos desde afuera
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
