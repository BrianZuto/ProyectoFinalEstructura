package model;

import java.util.LinkedList;

public class ArbolContenido {
    private NodoABB raiz;

    public ArbolContenido() {
        this.raiz = null;
    }

    public void insertar(Contenido contenido) {
        raiz = insertarRecursivo(raiz, contenido);
    }

    private NodoABB insertarRecursivo(NodoABB actual, Contenido contenido) {
        if (actual == null) {
            return new NodoABB(contenido);
        }

        if (contenido.getTema().compareToIgnoreCase(actual.contenido.getTema()) < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, contenido);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, contenido);
        }

        return actual;
    }

    // NUEVO: Devuelve contenidos ordenados
    public void inordenToList(LinkedList<Contenido> lista) {
        inordenToListRec(raiz, lista);
    }

    private void inordenToListRec(NodoABB nodo, LinkedList<Contenido> lista) {
        if (nodo != null) {
            inordenToListRec(nodo.izquierdo, lista);
            lista.add(nodo.contenido);
            inordenToListRec(nodo.derecho, lista);
        }
    }
}
