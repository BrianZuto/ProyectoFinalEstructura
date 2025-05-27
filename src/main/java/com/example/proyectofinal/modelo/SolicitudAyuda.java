package com.example.proyectofinal.modelo;

public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private User estudiante;
    private String tema;
    private int urgencia; // 1 = baja, 2 = media, 3 = alta

    public SolicitudAyuda(User estudiante, String tema, int urgencia) {
        this.estudiante = estudiante;
        this.tema = tema;
        this.urgencia = urgencia;
    }

    public User getEstudiante() { return estudiante; }
    public String getTema() { return tema; }
    public int getUrgencia() { return urgencia; }

    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia); // orden descendente
    }

    @Override
    public String toString() {
        return estudiante.getUsername() + " solicita ayuda en " + tema + " (urgencia: " + urgencia + ")";
    }
}
