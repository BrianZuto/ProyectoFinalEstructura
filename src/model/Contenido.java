package model;

import java.util.ArrayList;
import java.util.List;

public class Contenido {

    private String titulo;
    private String tipo;
    private boolean eliminado = false;
    private String descripcion;
    private String autor;
    private String tema;
    private List<Integer> valoraciones;

    public Contenido(String titulo, String tipo, String descripcion, String autor, String tema) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.tema = tema;
        this.valoraciones = new ArrayList<>();
    }
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public String getTema() {
        return tema;
    }
    public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public void setTipo(String tipo) {
    this.tipo = tipo;
}

public void setTema(String tema) {
    this.tema = tema;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}


    public void agregarValoracion(int valor) {
        if (valor >= 1 && valor <= 5) {
            valoraciones.add(valor);
        }
    }

    public double promedioValoraciones() {
        if (valoraciones == null || valoraciones.isEmpty()) return 0.0;
        int suma = 0;
        for (int v : valoraciones) {
            suma += v;
        }
        return (double) suma / valoraciones.size();
    }

    public List<Integer> getValoraciones() {
        return valoraciones;
    }
}
