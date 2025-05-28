package controller;

import model.Estudiante;
import model.Moderador;
import model.Usuario;

import java.util.LinkedList;

public class AuthController {

    private LinkedList<Usuario> usuariosRegistrados;
    private LinkedList<Usuario> usuariosActivos = new LinkedList<>();


    public AuthController() {
        this.usuariosRegistrados = new LinkedList<>();
        // Usuario por defecto para pruebas
        usuariosRegistrados.add(new Moderador("admin", "admin@correo.com", "1234"));
    }

    public boolean registrarEstudiante(String nombre, String correo, String clave, LinkedList<String> intereses) {
        if (buscarPorCorreo(correo) != null) return false;
        Estudiante nuevo = new Estudiante(nombre, correo, clave, intereses);
        usuariosRegistrados.add(nuevo);
        return true;
    }

    public Usuario login(String correo, String clave) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(correo) && u.getContraseña().equals(clave)) {
                return u;
            }
        }
        return null;
    }

    public void registrarSesionActiva(Usuario usuario) {
        if (!usuariosActivos.contains(usuario)) {
            usuariosActivos.add(usuario);
            }
    }

    public LinkedList<Usuario> getUsuariosActivos() {
        return usuariosActivos;
    }



    public boolean registrarModerador(String nombre, String correo, String clave) {
        if (buscarPorCorreo(correo) != null) return false;
            Moderador nuevo = new Moderador(nombre, correo, clave);
            usuariosRegistrados.add(nuevo);
            return true;
    }


    public Usuario buscarPorCorreo(String correo) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    public LinkedList<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    
}