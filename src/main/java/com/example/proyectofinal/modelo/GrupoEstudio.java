package com.example.proyectofinal.modelo;

import java.util.ArrayList;
import java.util.List;

public class GrupoEstudio {
    private String nombre;
    private List<User> miembros;
    private String tema;

    public GrupoEstudio(String nombre, String tema) {
        this.nombre = nombre;
        this.tema = tema;
        this.miembros = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getTema() { return tema; }
    public List<User> getMiembros() { return miembros; }

    public void agregarMiembro(User estudiante) {
        if (!miembros.contains(estudiante)) {
            miembros.add(estudiante);
            estudiante.unirseAGrupo(this);
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + tema + ") con " + miembros.size() + " miembros";
    }
}
