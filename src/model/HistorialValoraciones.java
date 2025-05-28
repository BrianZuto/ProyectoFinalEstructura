package model;

import java.util.LinkedList;

public class HistorialValoraciones {
    private LinkedList<Contenido> contenidosValorados;

    public HistorialValoraciones() {
        this.contenidosValorados = new LinkedList<>();
    }

    public void agregarContenido(Contenido c) {
        contenidosValorados.add(c);
    }

    public LinkedList<Contenido> getContenidosValorados() {
        return contenidosValorados;
    }
}
