package model;

import java.util.*;

public class GrafoAfinidad {
    private Map<Estudiante, List<Estudiante>> grafo;

    public GrafoAfinidad() {
        this.grafo = new HashMap<>();
    }

    public void agregarEstudiante(Estudiante e) {
        grafo.putIfAbsent(e, new ArrayList<>());
    }

    public void agregarConexion(Estudiante a, Estudiante b) {
        grafo.get(a).add(b);
        grafo.get(b).add(a); // no dirigido
    }

    public List<Estudiante> obtenerVecinos(Estudiante e) {
        return grafo.getOrDefault(e, new ArrayList<>());
    }

    public List<Estudiante> sugerencias(Estudiante origen) {
        Set<Estudiante> sugeridos = new HashSet<>();
        for (Estudiante amigo : obtenerVecinos(origen)) {
            for (Estudiante amigoDeAmigo : obtenerVecinos(amigo)) {
                if (!amigoDeAmigo.equals(origen) && !obtenerVecinos(origen).contains(amigoDeAmigo)) {
                    sugeridos.add(amigoDeAmigo);
                }
            }
        }
        return new ArrayList<>(sugeridos);
    }

    public int caminoMasCorto(Estudiante origen, Estudiante destino) {
        Map<Estudiante, Integer> distancias = new HashMap<>();
        Queue<Estudiante> cola = new LinkedList<>();
        distancias.put(origen, 0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            Estudiante actual = cola.poll();
            if (actual.equals(destino)) {
                return distancias.get(actual);
            }

            for (Estudiante vecino : obtenerVecinos(actual)) {
                if (!distancias.containsKey(vecino)) {
                    distancias.put(vecino, distancias.get(actual) + 1);
                    cola.add(vecino);
                }
            }
        }

        return -1; // sin camino
    }
}
