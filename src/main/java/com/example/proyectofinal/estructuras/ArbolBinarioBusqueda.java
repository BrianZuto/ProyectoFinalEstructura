package com.example.proyectofinal.estructuras;

import com.example.proyectofinal.modelo.Contenido;

public class ArbolBinarioBusqueda {
    private Nodo raiz;

    private static class Nodo {
        Contenido contenido;
        Nodo izquierda;
        Nodo derecha;

        Nodo(Contenido contenido) {
            this.contenido = contenido;
        }
    }

    public void insertar(Contenido nuevoContenido) {
        raiz = insertarRecursivo(raiz, nuevoContenido);
    }

    private Nodo insertarRecursivo(Nodo actual, Contenido nuevoContenido) {
        if (actual == null) return new Nodo(nuevoContenido);

        if (nuevoContenido.compareTo(actual.contenido) < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, nuevoContenido);
        } else if (nuevoContenido.compareTo(actual.contenido) > 0) {
            actual.derecha = insertarRecursivo(actual.derecha, nuevoContenido);
        }
        return actual;
    }

    public Contenido buscarPorTitulo(String titulo) {
        return buscarRecursivo(raiz, titulo);
    }

    private Contenido buscarRecursivo(Nodo actual, String titulo) {
        if (actual == null) return null;

        int comparacion = titulo.compareToIgnoreCase(actual.contenido.getTitulo());
        if (comparacion == 0) return actual.contenido;
        else if (comparacion < 0) return buscarRecursivo(actual.izquierda, titulo);
        else return buscarRecursivo(actual.derecha, titulo);
    }

    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(Nodo actual) {
        if (actual != null) {
            inOrdenRecursivo(actual.izquierda);
            System.out.println(actual.contenido);
            inOrdenRecursivo(actual.derecha);
        }
    }
}
