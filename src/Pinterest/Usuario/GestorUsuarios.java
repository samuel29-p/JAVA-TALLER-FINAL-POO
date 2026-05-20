package Pinterest.Usuario;

import Pinterest.Enum.TipoNotificacion;
import Pinterest.Exceptions.UsuarioNoEncontradoException;
import Pinterest.Notificaciones.GestorNotificaciones;
import Pinterest.Notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GestorUsuarios {

    private HashMap<String, Usuario> usuarios;

    private HashMap<String, List<String>> seguidores;

    private GestorNotificaciones gestorNotificaciones;

    public GestorUsuarios(GestorNotificaciones gestorNotificaciones) {
        this.usuarios = new HashMap<>();
        this.seguidores = new HashMap<>();
        this.gestorNotificaciones = gestorNotificaciones;
    }


    public void registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getId())) {
            System.out.println("Ya existe un usuario con ese id.");
            return;
        }
        usuarios.put(usuario.getId(), usuario);
        seguidores.put(usuario.getId(), new ArrayList<>());
        gestorNotificaciones.agregarObserver(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombre());
    }


    public Usuario buscarPorId(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("No se encontró usuario con id: " + id);
        }
        return usuario;
    }


    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios.values()) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    public List<Usuario> getTodosLosUsuarios() {
        return new ArrayList<>(usuarios.values());
    }


    public void seguir(String idSeguidor, String idSeguido) {
        Usuario seguidor = buscarPorId(idSeguidor);
        Usuario seguido  = buscarPorId(idSeguido);

        seguidor.seguir(seguido);
        seguidores.get(idSeguido).add(idSeguidor);

        Notificacion notif = new Notificacion(
                "N" + System.currentTimeMillis(),
                seguidor.getNombre() + " comenzó a seguirte.",
                TipoNotificacion.NUEVO_SEGUIDOR
        );
        seguido.recibirNotificacion(notif);

        System.out.println(seguidor.getNombre() + " ahora sigue a " + seguido.getNombre());
    }

    public void dejarDeSeguir(String idSeguidor, String idSeguido) {
        Usuario seguidor = buscarPorId(idSeguidor);
        Usuario seguido  = buscarPorId(idSeguido);
        seguidor.dejarDeSeguir(seguido);
        seguidores.get(idSeguido).remove(idSeguidor);
        System.out.println(seguidor.getNombre() + " dejó de seguir a " + seguido.getNombre());
    }

    public int getCantidadSeguidores(String idUsuario) {
        buscarPorId(idUsuario);
        return seguidores.getOrDefault(idUsuario, new ArrayList<>()).size();
    }


    public List<Usuario> getUsuariosOrdenadosPor(Comparator<Usuario> comparator) {
        List<Usuario> lista = new ArrayList<>(usuarios.values());
        lista.sort(comparator); // sort ordena la lista usando el comparator
        return lista;
    }

    public List<Usuario> getMasInfluyentes() {
        List<Usuario> lista = new ArrayList<>(usuarios.values());
        
        // Ordena de mayor a menor cantidad de seguidores
        lista.sort((a, b) -> getCantidadSeguidores(b.getId()) - getCantidadSeguidores(a.getId()));
        
        // Devuelve solo los primeros 10 
        return lista.subList(0, Math.min(10, lista.size()));
    }


    public double getTasaInteraccion(Usuario usuario) {
        if (usuario.getTotalPublicaciones() == 0) return 0.0;
        return (double) usuario.getTotalInteracciones() / usuario.getTotalPublicaciones();
    }


    public Estadisticas getEstadisticas(String idUsuario) {
        Usuario u = buscarPorId(idUsuario);
        return new Estadisticas(
                u.getNombre(),
                u.getTotalPublicaciones(),
                u.getTotalInteracciones(),
                getCantidadSeguidores(u.getId())
        );
    }
}
