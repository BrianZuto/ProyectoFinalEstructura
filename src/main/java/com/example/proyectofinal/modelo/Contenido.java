package com.example.proyectofinal.modelo;

public class Contenido implements Comparable<Contenido> {
    private String titulo;
    private String autor;
    private String tema;
    private String tipo; // video, archivo, enlace, etc.
    private int valoraciones;

    public Contenido(String titulo, String autor, String tema, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.tema = tema;
        this.tipo = tipo;
        this.valoraciones = 0;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getTema() { return tema; }
    public String getTipo() { return tipo; }
    public int getValoraciones() { return valoraciones; }

    public void valorar() {
        this.valoraciones++;
    }

    @Override
    public int compareTo(Contenido otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }

    @Override
    public String toString() {
        return "[" + titulo + " - " + tema + " - " + tipo + " - valoraciones: " + valoraciones + "]";
    }
}
