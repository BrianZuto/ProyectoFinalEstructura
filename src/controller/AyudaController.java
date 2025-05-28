package controller;

import model.ColaAyuda;
import model.SolicitudAyuda;
import model.Estudiante;

import java.util.List;

public class AyudaController {

    private ColaAyuda colaAyuda;

    public AyudaController() {
        this.colaAyuda = new ColaAyuda();
    }

    public void registrarSolicitud(Estudiante estudiante, String tema, int urgencia) {
        SolicitudAyuda solicitud = new SolicitudAyuda(estudiante, tema, urgencia);
        colaAyuda.agregarSolicitud(solicitud);
    }

    public SolicitudAyuda atenderSiguiente() {
        return colaAyuda.atenderSolicitud();
    }

    public List<SolicitudAyuda> verSolicitudes() {
        return colaAyuda.verSolicitudes();
    }

    public boolean estaVacia() {
        return colaAyuda.estaVacia();
    }
}
