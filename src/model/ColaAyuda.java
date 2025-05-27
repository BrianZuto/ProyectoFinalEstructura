package model;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;

/**
 * Cola de prioridad para gestionar solicitudes de ayuda académica.
 */
public class ColaAyuda {
    private PriorityQueue<SolicitudAyuda> cola;

    public ColaAyuda() {
        this.cola = new PriorityQueue<>();
    }

    public void agregarSolicitud(SolicitudAyuda solicitud) {
        cola.offer(solicitud);
    }

    public SolicitudAyuda atenderSolicitud() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public List<SolicitudAyuda> verSolicitudes() {
        return new ArrayList<>(cola);
    }
}
