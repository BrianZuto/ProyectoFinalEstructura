package controller;

import model.ArbolContenido;
import model.Contenido;

import java.util.LinkedList;

public class ContenidoController {
    private ArbolContenido arbol;

    public ContenidoController() {
        this.arbol = new ArbolContenido();
    }

    public void publicarContenido(Contenido contenido) {
        arbol.insertar(contenido);
    }

    public LinkedList<Contenido> obtenerContenidosOrdenados() {
        LinkedList<Contenido> lista = new LinkedList<>();
        arbol.inordenToList(lista);
        return lista;
    }

    public LinkedList<Contenido> getTopContenidos(int topN) {
        LinkedList<Contenido> lista = obtenerContenidosOrdenados();
        lista.sort((a, b) -> Double.compare(b.promedioValoraciones(), a.promedioValoraciones()));
        return new LinkedList<>(lista.subList(0, Math.min(topN, lista.size())));
    }

    public ArbolContenido getArbol() {
        return arbol;
    }
}
