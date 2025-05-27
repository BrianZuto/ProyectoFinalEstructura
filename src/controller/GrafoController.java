package controller;

import model.Contenido;
import model.Estudiante;
import model.GrupoEstudio;
import model.Usuario;

import java.util.*;

public class GrafoController {

    private Map<Estudiante, List<Estudiante>> grafo;

    public GrafoController() {
        grafo = new HashMap<>();
    }

    public void construirGrafoDesdeEstudiantes(List<Estudiante> estudiantes) {
        grafo.clear();

        // Inicializar nodos
        for (Estudiante est : estudiantes) {
            grafo.putIfAbsent(est, new ArrayList<>());
        }

        // Conexiones por intereses en común
        for (Estudiante a : estudiantes) {
            for (Estudiante b : estudiantes) {
                if (!a.equals(b) && !grafo.get(a).contains(b)) {
                    if (tienenInteresesComunes(a, b)) {
                        agregarConexion(a, b);
                    }
                }
            }
        }

        // NUEVO: Conexiones por contenidos valorados en común
        for (Estudiante a : estudiantes) {
            for (Estudiante b : estudiantes) {
                if (!a.equals(b) && !grafo.get(a).contains(b)) {
                    if (valoraronContenidoComun(a, b)) {
                        agregarConexion(a, b);
                    }
                }
            }
        }

        // NUEVO: Conexiones por grupos en común
        for (Estudiante a : estudiantes) {
            for (Estudiante b : estudiantes) {
                if (!a.equals(b) && !grafo.get(a).contains(b)) {
                    if (estanEnMismoGrupo(a, b)) {
                        agregarConexion(a, b);
                    }
                }
            }
        }
    }

    private boolean tienenInteresesComunes(Estudiante a, Estudiante b) {
        for (String interes : a.getIntereses()) {
            if (b.getIntereses().contains(interes)) {
                return true;
            }
        }
        return false;
    }

    private boolean valoraronContenidoComun(Estudiante a, Estudiante b) {
        for (Contenido c : a.getHistorialValoraciones().getContenidosValorados()) {
            if (b.getHistorialValoraciones().getContenidosValorados().contains(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean estanEnMismoGrupo(Estudiante a, Estudiante b) {
        for (GrupoEstudio ga : a.getGrupos()) {
            if (b.getGrupos().contains(ga)) {
                return true;
            }
        }
        return false;
    }

    private void agregarConexion(Estudiante a, Estudiante b) {
        grafo.get(a).add(b);
        grafo.get(b).add(a);
    }

    public List<Estudiante> sugerencias(Estudiante base) {
        List<Estudiante> sugeridos = new ArrayList<>();
        if (!grafo.containsKey(base)) return sugeridos;

        for (Estudiante vecino : grafo.get(base)) {
            for (Estudiante amigoDeAmigo : grafo.getOrDefault(vecino, List.of())) {
                if (!amigoDeAmigo.equals(base) && !grafo.get(base).contains(amigoDeAmigo)) {
                    if (!sugeridos.contains(amigoDeAmigo)) {
                        sugeridos.add(amigoDeAmigo);
                    }
                }
            }
        }
        return sugeridos;
    }

    public List<Estudiante> rutaMasCorta(Estudiante origen, Estudiante destino) {
        Map<Estudiante, Estudiante> predecesores = new HashMap<>();
        Set<Estudiante> visitados = new HashSet<>();
        Queue<Estudiante> cola = new LinkedList<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            Estudiante actual = cola.poll();
            if (actual.equals(destino)) break;

            for (Estudiante vecino : grafo.getOrDefault(actual, List.of())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesores.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        if (!predecesores.containsKey(destino)) return List.of();

        LinkedList<Estudiante> camino = new LinkedList<>();
        for (Estudiante at = destino; at != null; at = predecesores.get(at)) {
            camino.addFirst(at);
        }
        return camino;
    }

    public List<Estudiante> getVecinos(Estudiante estudiante) {
        return grafo.getOrDefault(estudiante, new ArrayList<>());
    }

    public List<List<Estudiante>> detectarClusters() {
        List<List<Estudiante>> clusters = new ArrayList<>();
        Set<Estudiante> visitados = new HashSet<>();

        for (Estudiante est : grafo.keySet()) {
            if (!visitados.contains(est)) {
                List<Estudiante> componente = new ArrayList<>();
                Queue<Estudiante> cola = new LinkedList<>();
                cola.add(est);
                visitados.add(est);

                while (!cola.isEmpty()) {
                    Estudiante actual = cola.poll();
                    componente.add(actual);
                    for (Estudiante vecino : grafo.getOrDefault(actual, List.of())) {
                        if (!visitados.contains(vecino)) {
                            visitados.add(vecino);
                            cola.add(vecino);
                        }
                    }
                }

                clusters.add(componente);
            }
        }

        return clusters;
    }
}
