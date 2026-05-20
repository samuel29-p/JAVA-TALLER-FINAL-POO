package Pinterest;

import Pinterest.Contenido.Reporte;
import Pinterest.Enum.EstadoReporte;
import Pinterest.Enum.TipoNotificacion;
import Pinterest.Enum.TipoReporte;
import Pinterest.Estadisticas.Estadisticas;
import Pinterest.Exceptions.ContenidoNoDisponibleException;
import Pinterest.Exceptions.PermisoDenegadoException;
import Pinterest.Exceptions.UsuarioNoEncontradoException;
import Pinterest.Gestores.GestorBusqueda;
import Pinterest.Gestores.GestorContenido;
import Pinterest.Interaccion.Comentario;
import Pinterest.Interaccion.Compartido;
import Pinterest.Interaccion.Guardado;
import Pinterest.Interaccion.Like;
import Pinterest.Notificaciones.GestorNotificaciones;
import Pinterest.Notificaciones.Notificacion;
import Pinterest.Pins.Pin;
import Pinterest.Pins.PinFactory;
import Pinterest.Pins.PinIdea;
import Pinterest.Pins.PinVideo;
import Pinterest.Recomendacion.*;
import Pinterest.Tablero.Tablero;
import Pinterest.Usuario.*;
import Pinterest.Contenido.Historia;
import Pinterest.Contenido.Contenido;

import java.time.LocalDateTime;
import java.util.List;

public class MainFinal {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("      USUARIOS Y CONTENIDO");
        System.out.println("========================================");

        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        GestorUsuarios gestorUsuarios = new GestorUsuarios(gestorNotificaciones);
        GestorContenido gestorContenido = GestorContenido.getInstancia();

        UsuarioEstandar samuel = new UsuarioEstandar("u1", "Samuel", "samuel@mail.com");
        UsuarioVerificado ana = new UsuarioVerificado("u2", "Ana", "ana@mail.com", "Diseño");
        UsuarioVerificado carlos = new UsuarioVerificado("u3", "Carlos", "carlos@mail.com", "Tecnologia");
        Moderador mod = new Moderador("u4", "ModAdmin", "mod@mail.com", "Colombia");

        samuel.completarPerfil("Amante del diseño y la tecnologia");

        gestorUsuarios.registrarUsuario(samuel);
        gestorUsuarios.registrarUsuario(ana);
        gestorUsuarios.registrarUsuario(carlos);
        gestorUsuarios.registrarUsuario(mod);

        Pin pin1 = PinFactory.crearPin("Diseño minimalista", ana, "Diseño", "img1.jpg", "Tendencias 2025");
        PinVideo pinVideo = PinFactory.crearPinVideo("Tutorial Figma", carlos, "Tecnologia", "img2.jpg", "video1.mp4", 180);
        PinIdea pinIdea = PinFactory.crearPinIdea("Como decorar tu cuarto", ana, "Hogar", "img3.jpg");
        pinIdea.agregarPaso("Elige una paleta de colores");
        pinIdea.agregarPaso("Compra los muebles necesarios");
        pinIdea.agregarPaso("Organiza por zonas");

        ContenidoFactory historiaFactory = new HistoriaFactory();
        Historia historia = (Historia) historiaFactory.crear(10, "Historia del dia", ana, "Lifestyle");

        gestorContenido.agregar(pin1);
        gestorContenido.agregar(pinVideo);
        gestorContenido.agregar(pinIdea);
        gestorContenido.agregar(historia);

        ana.incrementarPublicaciones();
        ana.incrementarPublicaciones();
        carlos.incrementarPublicaciones();

        System.out.println("Usuarios creados: " + gestorUsuarios.getTodosLosUsuarios().size());
        System.out.println("Contenidos registrados: " + gestorContenido.getTodos().size());
        System.out.println("Historia expira en: " + historia.tiempoRestante() + " minutos");

        System.out.println("\n========================================");
        System.out.println("      RELACIONES DE SEGUIMIENTO");
        System.out.println("========================================");

        gestorUsuarios.seguir("u1", "u2");
        gestorUsuarios.seguir("u1", "u3");
        gestorUsuarios.seguir("u3", "u2");

        System.out.println("Samuel sigue a Ana: " + samuel.sigueA("u2"));
        System.out.println("Seguidores de Ana: " + gestorUsuarios.getCantidadSeguidores("u2"));

        List<Usuario> ordenados = gestorUsuarios.getUsuariosOrdenadosPor(
                (a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre())
        );
        System.out.println("Usuarios ordenados por nombre:");
        for (Usuario u : ordenados) {
            System.out.println("  - " + u.getNombre());
        }

        gestorUsuarios.dejarDeSeguir("u1", "u3");
        System.out.println("Samuel sigue a Carlos: " + samuel.sigueA("u3"));
        gestorUsuarios.seguir("u1", "u3");

        System.out.println("\n========================================");
        System.out.println("      INTERACCIONES Y TABLEROS");
        System.out.println("========================================");

        Tablero tableroSamuel = new Tablero(1, "Mis favoritos", "Pins que me gustan", samuel);
        tableroSamuel.hacerColaborativo();
        tableroSamuel.agregarColaborador(ana);
        tableroSamuel.agregarPin(pin1);

        Tablero tableroSecreto = new Tablero(2, "Ideas privadas", "Solo para mi", samuel);
        tableroSecreto.hacerSecreto();

        Like like1 = new Like(1, samuel, pin1);
        like1.ejecutar();
        samuel.incrementarInteracciones();
        ana.incrementarInteracciones();

        Comentario comentario1 = new Comentario(2, samuel, pin1, "Me encanta este diseño!");
        comentario1.ejecutar();

        Comentario respuesta1 = new Comentario(3, ana, pin1, "Gracias Samuel!", comentario1);
        respuesta1.ejecutar();
        comentario1.imprimirArbol(0);
        System.out.println("Total respuestas en comentario: " + comentario1.contarRespuestasTotal());

        Guardado guardado1 = new Guardado(4, samuel, pin1, tableroSamuel);
        guardado1.ejecutar();

        Compartido compartido1 = new Compartido(5, carlos, pinVideo, "amigo@mail.com");
        compartido1.ejecutar();

        System.out.println("Pins en tablero de Samuel: " + tableroSamuel.getPins().size());

        System.out.println("\n========================================");
        System.out.println("      NOTIFICACIONES");
        System.out.println("========================================");

        Notificacion notifLike = new Notificacion("N1", samuel.getNombre() + " dio like a tu pin", TipoNotificacion.NUEVO_LIKE);
        gestorNotificaciones.notificarObservers(notifLike);

        Notificacion notifComentario = new Notificacion("N2", samuel.getNombre() + " comentó en tu pin", TipoNotificacion.NUEVO_COMENTARIO);
        gestorNotificaciones.notificarObservers(notifComentario);

        Notificacion notifPin = new Notificacion("N3", ana.getNombre() + " publicó contenido nuevo", TipoNotificacion.NUEVO_PIN);
        gestorNotificaciones.notificarObservers(notifPin);

        System.out.println("Notificaciones de Samuel: " + samuel.getNotificaciones().size());
        System.out.println("Notificaciones de Ana: " + ana.getNotificaciones().size());

        ana.getNotificaciones().get(0).marcarleida();
        System.out.println("Primera notificacion de Ana leida: " + ana.getNotificaciones().get(0).isLeida());

        System.out.println("\n========================================");
        System.out.println("      RECOMENDACIONES");
        System.out.println("========================================");

        List<Contenido> catalogo = gestorContenido.getTodos();
        MotorRecomendacion motor = new MotorRecomendacion();

        motor.setEstrategia(new EstrategiaPopularidad(gestorUsuarios, catalogo));
        List<Contenido> porPopularidad = motor.recomendar(samuel);
        System.out.println("Recomendaciones por popularidad para Samuel:");
        for (Contenido c : porPopularidad) {
            System.out.println("  - " + c.getTitulo());
        }

        motor.setEstrategia(new EstrategiaRed(gestorUsuarios, catalogo));
        List<Contenido> porRed = motor.recomendar(samuel);
        System.out.println("Recomendaciones por red para Samuel:");
        for (Contenido c : porRed) {
            System.out.println("  - " + c.getTitulo());
        }

        motor.setEstrategia(new EstrategiaIntereses(gestorUsuarios, catalogo));
        List<Contenido> porIntereses = motor.recomendar(samuel);
        System.out.println("Recomendaciones por intereses para Samuel:");
        for (Contenido c : porIntereses) {
            System.out.println("  - " + c.getTitulo());
        }

        System.out.println("\n========================================");
        System.out.println("      MANEJO DE ERRORES Y REPORTES");
        System.out.println("========================================");

        try {
            gestorUsuarios.buscarPorId("usuarioInexistente");
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Excepcion controlada: " + e.getMessage());
        }

        try {
            if (!mod.puedePublicar()) {
                throw new PermisoDenegadoException("El moderador no tiene permiso para publicar contenido");
            }
        } catch (PermisoDenegadoException e) {
            System.out.println("Excepcion controlada: " + e.getMessage());
        }

        Reporte reporte = new Reporte(1, TipoReporte.SPAM, "Este pin es publicidad engañosa");
        System.out.println("Reporte creado: " + reporte);
        reporte.setEstado(EstadoReporte.EN_REVISION);
        mod.resolverReporte("1");
        reporte.setEstado(EstadoReporte.RESUELTO);
        System.out.println("Estado final del reporte: " + reporte.getEstado());

        Estadisticas stats = gestorContenido.producirEstadisticas();
        stats.setTotalUsuarios(gestorUsuarios.getTodosLosUsuarios().size());
        stats.setTotalInteracciones(3);
        stats.setTotalPublicaciones(3);
        System.out.println(stats.Exportar());

        System.out.println(gestorContenido.Exportar());

        Pinterest.Usuario.Estadisticas statsUsuario = gestorUsuarios.getEstadisticas("u2");
        System.out.println(statsUsuario);

        System.out.println("\n========================================");
        System.out.println("      PERFIL DE USUARIO");
        System.out.println("========================================");


        System.out.println("Email de Samuel: "           + samuel.getEmail());
        System.out.println("Bio de Samuel: "             + samuel.getBio());
        System.out.println("Perfil completo de Samuel: " + samuel.isPerfilCompleto());
        System.out.println("Samuel sigue a "             + samuel.getCantidadSeguidos() + " usuario(s)");


        List<Usuario> encontrados = gestorUsuarios.buscarPorNombre("Ana");
        System.out.println("Busqueda por nombre 'Ana': " + encontrados.size() + " resultado(s)");

        System.out.println("\n========================================");
        System.out.println("      ESTADISTICAS DE USUARIO DETALLADAS");
        System.out.println("========================================");


        System.out.println("Nombre de usuario:    " + statsUsuario.getNombreUsuario());
        System.out.println("Total publicaciones:  " + statsUsuario.getTotalPublicaciones());
        System.out.println("Total interacciones:  " + statsUsuario.getTotalInteracciones());
        System.out.println("Total seguidores:     " + statsUsuario.getTotalSeguidores());
        System.out.println("Tasa de interaccion:  " + statsUsuario.getTasaInteraccion());

        System.out.println("\n========================================");
        System.out.println("      ESTADISTICAS GLOBALES DETALLADAS");
        System.out.println("========================================");


        System.out.println("Total contenidos:    " + stats.getTotalContenidos());
        System.out.println("Total usuarios:      " + stats.getTotalUsuarios());
        System.out.println("Total interacciones: " + stats.getTotalInteracciones());
        System.out.println("Total publicaciones: " + stats.getTotalPublicaciones());
        Contenido masReciente = stats.getContenidoMasReciente();
        if (masReciente != null) {
            System.out.println("Contenido mas reciente: " + masReciente.getTitulo());
        }

        System.out.println("\n========================================");
        System.out.println("      GESTION DE TABLERO");
        System.out.println("========================================");


        System.out.println("Propietario del tablero: "       + tableroSamuel.getPropietario().getNombre());
        System.out.println("tableroSamuel es secreto: "      + tableroSamuel.isEsSecreto());
        System.out.println("tableroSecreto es secreto: "     + tableroSecreto.isEsSecreto());
        System.out.println("Colaboradores actuales: "        + tableroSamuel.getColaboradores().size());

        tableroSamuel.eliminarColaborador(ana);
        System.out.println("Colaboradores tras quitar a Ana: " + tableroSamuel.getColaboradores().size());

        tableroSamuel.agregarPin(pinIdea);
        tableroSamuel.eliminarPin(pinIdea);
        System.out.println("Pins tras agregar y eliminar pinIdea: " + tableroSamuel.getPins().size());

        System.out.println("\n========================================");
        System.out.println("      DETALLES DE PINS");
        System.out.println("========================================");


        System.out.println("URL imagen pin1 original: " + pin1.getUrlImagen());
        pin1.setUrlImagen("img1_actualizada.jpg");
        System.out.println("URL imagen pin1 actualizada: " + pin1.getUrlImagen());
        pin1.setDescripcion("Tendencias minimalistas 2025 - edicion especial");
        System.out.println("Descripcion pin1 (abstract): " + pin1.getDescripcion());


        System.out.println("URL video original: "   + pinVideo.getUrlVideo());
        System.out.println("Duracion original: "    + pinVideo.getDuracionSegundos() + "s");
        pinVideo.setUrlVideo("video1_hd.mp4");
        pinVideo.setDuracionSegundos(210);
        System.out.println("URL video actualizada: " + pinVideo.getUrlVideo());
        System.out.println("Duracion actualizada: "  + pinVideo.getDuracionSegundos() + "s");
        System.out.println("Descripcion pinVideo (abstract): " + pinVideo.getDescripcion());


        System.out.println("Pasos actuales: " + pinIdea.getPasos());
        pinIdea.eliminarPaso(0);
        System.out.println("Pasos tras eliminar indice 0: " + pinIdea.getPasos());
        System.out.println("Descripcion pinIdea (abstract): " + pinIdea.getDescripcion());


        System.out.println("Descripcion historia (abstract): " + historia.getDescripcion());

        System.out.println("\n========================================");
        System.out.println("      DETALLES DE NOTIFICACIONES");
        System.out.println("========================================");


        Notificacion primeraNotifSamuel = samuel.getNotificaciones().get(0);
        System.out.println("Mensaje notif Samuel: " + primeraNotifSamuel.getMensaje());
        System.out.println("Tipo notif Samuel:    " + primeraNotifSamuel.getTipo());
        System.out.println("Fecha notif Samuel:   " + primeraNotifSamuel.getFecha());

        System.out.println("\n========================================");
        System.out.println("      INTERACCIONES DETALLADAS");
        System.out.println("========================================");


        System.out.println("Tablero destino actual: " + guardado1.getTableroDestino().getNombre());
        guardado1.setTableroDestino(tableroSecreto);
        System.out.println("Tablero destino nuevo:  " + guardado1.getTableroDestino().getNombre());


        System.out.println("Destinatario actual: " + compartido1.getDestinatario());
        compartido1.setDestinatario("otro@mail.com");
        System.out.println("Destinatario nuevo:  " + compartido1.getDestinatario());


        System.out.println("Texto comentario1:              " + comentario1.getText());
        System.out.println("comentario1 es respuesta:       " + comentario1.esRespuesta());
        System.out.println("respuesta1 es respuesta:        " + respuesta1.esRespuesta());
        System.out.println("original de respuesta1:         " + respuesta1.getComentarioPadre().getText());
        System.out.println("Respuestas de comentario1:      " + comentario1.getRespuestas().size());
        comentario1.setTexto("Me encanta este diseño, amo esta app, quiero mas contenido asi! (editado)");
        System.out.println("Texto actualizado comentario1:  " + comentario1.getText());
        comentario1.eliminarRespuesta(respuesta1);
        System.out.println("Respuestas tras eliminar:       " + comentario1.getRespuestas().size());

        System.out.println("\n========================================");
        System.out.println("      BUSQUEDA AVANZADA");
        System.out.println("========================================");


        GestorBusqueda gestorBusqueda = GestorBusqueda.getInstancia();

        List<Contenido> porTitulo = gestorBusqueda.buscarPorTitulo("Tutorial");
        System.out.println("Por titulo 'Tutorial':       " + porTitulo.size() + " resultado(s)");

        List<Contenido> porCategoria = gestorBusqueda.filtrarPorCategoria("Diseño");
        System.out.println("Por categoria 'Diseño':      " + porCategoria.size() + " resultado(s)");

        List<Contenido> porAutor = gestorBusqueda.buscarPorAutor("Ana");
        System.out.println("Por autor 'Ana':             " + porAutor.size() + " resultado(s)");

        List<Contenido> porEtiqueta = gestorBusqueda.buscarPorEtiqueta("minimalismo");
        System.out.println("Por etiqueta 'minimalismo':  " + porEtiqueta.size() + " resultado(s)");

        LocalDateTime desde = LocalDateTime.now().minusDays(1);
        LocalDateTime hasta = LocalDateTime.now().plusDays(1);
        List<Contenido> porFecha = gestorBusqueda.filtrarPorFecha(desde, hasta);
        System.out.println("Por fecha (rango de hoy):    " + porFecha.size() + " resultado(s)");

        List<Contenido> avanzado = gestorBusqueda.buscarAvanzado(null, "Tecnologia", desde, hasta);
        System.out.println("Avanzado cat 'Tecnologia':   " + avanzado.size() + " resultado(s)");

        System.out.println("\n========================================");
        System.out.println("      OBSERVER Y ELIMINACION DE CONTENIDO");
        System.out.println("========================================");


        gestorNotificaciones.eliminarObserver(carlos);
        System.out.println("Carlos eliminado como observer");


        try {
            gestorContenido.eliminar(historia.getId());
            System.out.println("Historia eliminada. Contenidos restantes: " + gestorContenido.getTodos().size());
        } catch (ContenidoNoDisponibleException e) {
            System.out.println("Error al eliminar contenido: " + e.getMessage());
        }
    }
}
