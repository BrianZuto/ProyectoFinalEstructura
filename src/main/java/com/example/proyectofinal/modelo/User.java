package com.example.proyectofinal.modelo;

import com.example.proyectofinal.estructuras.ListaEnlazada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private String username;
    private String password;
    private String rol; // "estudiante" o "moderador"

    private List<String> intereses;
    private ListaEnlazada<Contenido> contenidosPublicados;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    private Set<User> conexiones; // Grafo de afinidad

    public User(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.intereses = new ArrayList<>();
        this.contenidosPublicados = new ListaEnlazada<>();
        this.gruposEstudio = new ListaEnlazada<>();
        this.conexiones = new HashSet<>();
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String rol) { this.rol = rol; }

    public List<String> getIntereses() { return intereses; }
    public ListaEnlazada<Contenido> getContenidosPublicados() { return contenidosPublicados; }
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() { return gruposEstudio; }
    public Set<User> getConexiones() { return conexiones; }

    // Métodos de lógica
    public void agregarInteres(String interes) {
        if (!intereses.contains(interes)) {
            intereses.add(interes);
        }
    }

    public void publicarContenido(Contenido contenido) {
        contenidosPublicados.agregar(contenido);
    }

    public void unirseAGrupo(GrupoEstudio grupo) {
        gruposEstudio.agregar(grupo);
    }

    public void conectarCon(User otroUsuario) {
        if (!conexiones.contains(otroUsuario)) {
            conexiones.add(otroUsuario);
            otroUsuario.getConexiones().add(this); // conexión bidireccional
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User otro = (User) obj;
        return username.equals(otro.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username + " (" + rol + ")";
    }
}
