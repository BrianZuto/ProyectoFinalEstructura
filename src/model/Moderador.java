package model;

import java.util.LinkedList;

public class Moderador extends Usuario {
    public Moderador(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, new LinkedList<>());
    }

    public void eliminarContenido(Contenido contenido) {
        contenido.setEliminado(true);
    }

    public void bloquearUsuario(Usuario usuario) {
        // implementación opcional
    }
}
