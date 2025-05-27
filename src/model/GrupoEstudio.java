package model;

import java.util.LinkedList;

public class GrupoEstudio {
    private String tema;
    private LinkedList<Estudiante> participantes;
    private LinkedList<Contenido> recursosCompartidos;

    public GrupoEstudio(String tema) {
        this.tema = tema;
        this.participantes = new LinkedList<>();
        this.recursosCompartidos = new LinkedList<>();
    }

    public void agregarParticipante(Estudiante e) {
        participantes.add(e);
    }

    public void agregarRecurso(Contenido c) {
        recursosCompartidos.add(c);
    }

    public String getTema() { return tema; }
    public LinkedList<Estudiante> getParticipantes() { return participantes; }
    public LinkedList<Contenido> getRecursosCompartidos() { return recursosCompartidos; }
}
