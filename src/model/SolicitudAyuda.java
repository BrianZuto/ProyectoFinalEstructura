package model;

import model.SolicitudAyuda;


public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private Estudiante estudiante;
    private String tema;
    private int urgencia;

    public SolicitudAyuda(Estudiante estudiante, String tema, int urgencia) {
        this.estudiante = estudiante;
        this.tema = tema;
        this.urgencia = urgencia;
    }

    public int getUrgencia() { return urgencia; }
    public Estudiante getEstudiante() { return estudiante; }
    public String getTema() { return tema; }

    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia); // mayor urgencia = mayor prioridad
    }
}
