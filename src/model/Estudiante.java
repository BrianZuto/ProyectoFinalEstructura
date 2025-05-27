package model;

import java.util.LinkedList;

public class Estudiante extends Usuario {
    private LinkedList<Contenido> contenidosPublicados;
    private LinkedList<Mensaje> mensajesRecibidos;
    private LinkedList<GrupoEstudio> grupos;
    private HistorialValoraciones historialValoraciones;
    private LinkedList<Mensaje> mensajesEnviados;


    public Estudiante(String nombre, String correo, String contraseña, LinkedList<String> intereses) {
        super(nombre, correo, contraseña, intereses);
        this.contenidosPublicados = new LinkedList<>();
        this.mensajesRecibidos = new LinkedList<>();
        this.grupos = new LinkedList<>();
        this.historialValoraciones = new HistorialValoraciones();
        this.mensajesEnviados = new LinkedList<>();

    }
    
    public LinkedList<Mensaje> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public void agregarMensajeEnviado(Mensaje mensaje) {
        mensajesEnviados.add(mensaje);
    }


    public LinkedList<Contenido> getContenidosPublicados() { return contenidosPublicados; }
    public LinkedList<Mensaje> getMensajesRecibidos() { return mensajesRecibidos; }
    public LinkedList<GrupoEstudio> getGrupos() { return grupos; }
    public HistorialValoraciones getHistorialValoraciones() { return historialValoraciones; }
}
