package model;

public class NodoABB {
    public Contenido contenido;
    public NodoABB izquierdo;
    public NodoABB derecho;

    public NodoABB(Contenido contenido) {
        this.contenido = contenido;
        this.izquierdo = null;
        this.derecho = null;
    }
}
