package com.example.proyectofinal.estructuras;

import java.util.Iterator;

public class ListaEnlazada<T> implements Iterable<T> {

    private Nodo<T> cabeza;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public boolean contiene(T elemento) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(elemento)) return true;
            actual = actual.siguiente;
        }
        return false;
    }

    public void imprimir() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            public boolean hasNext() {
                return actual != null;
            }

            public T next() {
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
}
