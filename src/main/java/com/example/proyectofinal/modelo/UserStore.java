package com.example.proyectofinal.modelo;

import java.util.ArrayList;
import java.util.List;

public class UserStore {

    private static List<User> usuarios = new ArrayList<>();

    static {
        // Usuario predeterminado
        usuarios.add(new User("admin", "admin123", "moderador"));
        usuarios.add(new User("juan", "123", "estudiante"));
    }

    public static void registrarUsuario(User user) {
        usuarios.add(user);
    }

    public static User autenticar(String username, String password) {
        for (User u : usuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static boolean existeUsuario(String username) {
        return usuarios.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public static List<User> getUsuarios() {
        return usuarios;
    }
}