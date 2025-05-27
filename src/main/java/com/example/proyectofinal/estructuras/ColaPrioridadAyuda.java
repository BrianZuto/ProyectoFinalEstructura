package com.example.proyectofinal.estructuras;

import com.example.proyectofinal.modelo.SolicitudAyuda;
import javafx.scene.control.ListView;

public class ColaPrioridadAyuda {
    private Nodo frente;

    private static class Nodo {
        SolicitudAyuda solicitud;
        Nodo siguiente;

        Nodo(SolicitudAyuda solicitud) {
            this.solicitud = solicitud;
        }
    }

    public void encolar(SolicitudAyuda nueva) {
        Nodo nuevoNodo = new Nodo(nueva);

        if (frente == null || nueva.compareTo(frente.solicitud) < 0) {
            nuevoNodo.siguiente = frente;
            frente = nuevoNodo;
        } else {
            Nodo actual = frente;
            while (actual.siguiente != null && nueva.compareTo(actual.siguiente.solicitud) >= 0) {
                actual = actual.siguiente;
            }
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
    }

    public SolicitudAyuda desencolar() {
        if (frente == null) return null;
        SolicitudAyuda temp = frente.solicitud;
        frente = frente.siguiente;
        return temp;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    // ✅ Este método debe estar dentro de esta misma clase
    public void imprimirColaEn(ListView<String> listView) {
        Nodo actual = frente;
        while (actual != null) {
            listView.getItems().add(actual.solicitud.toString());
            actual = actual.siguiente;
        }
    }
}
