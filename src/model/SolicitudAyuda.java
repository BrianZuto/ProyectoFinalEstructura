package model;

import java.util.LinkedList;

public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private Estudiante estudiante; // Estudiante que solicita ayuda
    private String tema;            // Tema de la solicitud
    private int urgencia;          // Nivel de urgencia de la solicitud
    private LinkedList<Mensaje> respuestas; // Lista para almacenar respuestas

    // Constructor
    public SolicitudAyuda(Estudiante estudiante, String tema, int urgencia) {
        this.estudiante = estudiante;
        this.tema = tema;
        this.urgencia = urgencia;
        this.respuestas = new LinkedList<>(); // Inicializa la lista de respuestas
    }

    // Método para agregar una respuesta a la solicitud
    public void agregarRespuesta(Mensaje respuesta) {
        respuestas.add(respuesta); // Agrega la respuesta a la lista
    }

    // Método para obtener la lista de respuestas
    public LinkedList<Mensaje> getRespuestas() {
        return respuestas; // Devuelve la lista de respuestas
    }

    // Métodos getters
    public Estudiante getEstudiante() {
        return estudiante; // Devuelve el estudiante que hizo la solicitud
    }

    public String getTema() {
        return tema; // Devuelve el tema de la solicitud
    }

    public int getUrgencia() {
        return urgencia; // Devuelve el nivel de urgencia
    }

    // Método de comparación para ordenar solicitudes por urgencia
    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(this.urgencia, otra.urgencia); // Compara por urgencia
    }

    // Método toString para representar la solicitud como una cadena
    @Override
    public String toString() {
        return "SolicitudAyuda{" +
                "estudiante=" + estudiante.getNombre() +
                ", tema='" + tema + '\'' +
                ", urgencia=" + urgencia +
                ", respuestas=" + respuestas +
                '}';
    }
}
