package Pinterest;

import Pinterest.Contenido.Reporte;
import Pinterest.Enum.EstadoReporte;
import Pinterest.Enum.TipoNotificacion;
import Pinterest.Enum.TipoReporte;
import Pinterest.Estadisticas.Estadisticas;
import Pinterest.Exceptions.PermisoDenegadoException;
import Pinterest.Exceptions.UsuarioNoEncontradoException;
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
    }
}
