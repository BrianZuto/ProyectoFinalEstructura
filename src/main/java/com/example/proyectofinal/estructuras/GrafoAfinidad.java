package com.example.proyectofinal.estructuras;

import com.example.proyectofinal.modelo.User;

import java.util.*;

public class GrafoAfinidad {
    private Map<User, Set<User>> adyacencias;

    public GrafoAfinidad() {
        this.adyacencias = new HashMap<>();
    }

    public void agregarUsuario(User u) {
        adyacencias.putIfAbsent(u, new HashSet<>());
    }

    public void conectarUsuarios(User u1, User u2) {
        agregarUsuario(u1);
        agregarUsuario(u2);
        adyacencias.get(u1).add(u2);
        adyacencias.get(u2).add(u1); // no dirigido
        u1.conectarCon(u2); // mantener sincronizado con User
    }

    public Set<User> obtenerConexiones(User u) {
        return adyacencias.getOrDefault(u, new HashSet<>());
    }

    // Amigos de amigos (recomendaciones)
    public Set<User> sugerenciasDeAmigos(User u) {
        Set<User> sugerencias = new HashSet<>();
        Set<User> directos = obtenerConexiones(u);

        for (User amigo : directos) {
            for (User sugerido : obtenerConexiones(amigo)) {
                if (!sugerido.equals(u) && !directos.contains(sugerido)) {
                    sugerencias.add(sugerido);
                }
            }
        }

        return sugerencias;
    }

    // BFS para ruta más corta entre dos usuarios
    public List<User> caminoMasCorto(User origen, User destino) {
        Map<User, User> previo = new HashMap<>();
        Queue<User> cola = new LinkedList<>();
        Set<User> visitados = new HashSet<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            User actual = cola.poll();

            if (actual.equals(destino)) break;

            for (User vecino : obtenerConexiones(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    previo.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        // reconstruir camino
        List<User> camino = new LinkedList<>();
        for (User at = destino; at != null; at = previo.get(at)) {
            camino.add(0, at);
        }

        if (!camino.isEmpty() && camino.get(0).equals(origen)) {
            return camino;
        } else {
            return new ArrayList<>(); // no hay conexión
        }
    }

    // Para visualización o depuración
    public void imprimirGrafo() {
        for (User u : adyacencias.keySet()) {
            System.out.println(u.getUsername() + " conectado con: " +
                    adyacencias.get(u).stream().map(User::getUsername).toList());
        }
    }
}
