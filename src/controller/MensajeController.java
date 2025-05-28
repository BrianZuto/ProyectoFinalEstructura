package controller;

import model.Estudiante;
import model.Mensaje;

import java.util.LinkedList;

public class MensajeController {

    public void enviarMensaje(Estudiante emisor, Estudiante receptor, String contenido, String fecha) {
        Mensaje mensaje = new Mensaje(emisor.getNombre(), receptor.getNombre(), contenido, fecha);
        receptor.getMensajesRecibidos().add(mensaje);
        emisor.agregarMensajeEnviado(mensaje); 
    }


    public LinkedList<Mensaje> obtenerMensajes(Estudiante estudiante) {
        return estudiante.getMensajesRecibidos();
    }
}
