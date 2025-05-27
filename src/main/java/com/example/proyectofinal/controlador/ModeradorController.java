package com.example.proyectofinal.controlador;

import com.example.proyectofinal.estructuras.GrafoAfinidad;
import com.example.proyectofinal.modelo.Contenido;
import com.example.proyectofinal.modelo.User;
import com.example.proyectofinal.modelo.UserStore;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;
import java.util.stream.Collectors;

public class ModeradorController {

    @FXML
    private ListView<String> listReportes;

    @FXML
    private Pane grafoPane;

    private GrafoAfinidad grafo = new GrafoAfinidad();
    private List<User> usuarios;

    @FXML
    public void initialize() {
        listReportes.getItems().addAll(
                "Top 5 contenidos más valorados",
                "Estudiantes con más conexiones",
                "Caminos más cortos entre estudiantes",
                "Clústeres de afinidad",
                "Nivel de participación por usuario"
        );

        usuarios = new ArrayList<>(UserStore.getUsuarios());

        if (usuarios.size() >= 4) {
            grafo.conectarUsuarios(usuarios.get(0), usuarios.get(1));
            grafo.conectarUsuarios(usuarios.get(1), usuarios.get(2));
            grafo.conectarUsuarios(usuarios.get(2), usuarios.get(3));
            grafo.conectarUsuarios(usuarios.get(0), usuarios.get(3));
        }

        mostrarGrafo(usuarios);

        listReportes.setOnMouseClicked(event -> {
            String seleccionado = listReportes.getSelectionModel().getSelectedItem();
            if (seleccionado == null) return;

            switch (seleccionado) {
                case "Top 5 contenidos más valorados" -> mostrarTopContenidos();
                case "Estudiantes con más conexiones" -> mostrarTopConectados();
                case "Caminos más cortos entre estudiantes" -> mostrarRutaMasCorta();
                case "Clústeres de afinidad" -> mostrarClusters();
                case "Nivel de participación por usuario" -> mostrarParticipacion();
            }
        });
    }

    private void mostrarGrafo(List<User> nodos) {
        grafoPane.getChildren().clear();
        Map<User, Double[]> posiciones = new HashMap<>();

        double centroX = grafoPane.getPrefWidth() / 2;
        double centroY = grafoPane.getPrefHeight() / 2;
        double radio = 100;
        int total = nodos.size();

        for (int i = 0; i < total; i++) {
            double angle = 2 * Math.PI * i / total;
            double x = centroX + radio * Math.cos(angle);
            double y = centroY + radio * Math.sin(angle);
            posiciones.put(nodos.get(i), new Double[]{x, y});
        }

        for (User u : nodos) {
            for (User conectado : grafo.obtenerConexiones(u)) {
                if (nodos.indexOf(u) < nodos.indexOf(conectado)) {
                    Double[] p1 = posiciones.get(u);
                    Double[] p2 = posiciones.get(conectado);
                    grafoPane.getChildren().add(new Line(p1[0], p1[1], p2[0], p2[1]));
                }
            }
        }

        for (User u : nodos) {
            Double[] pos = posiciones.get(u);
            grafoPane.getChildren().addAll(
                    new Circle(pos[0], pos[1], 20),
                    new Text(pos[0] - 15, pos[1] + 5, u.getUsername())
            );
        }
    }

    private void mostrarTopContenidos() {
        List<Contenido> todos = new ArrayList<>();
        for (User u : usuarios) {
            for (Contenido c : u.getContenidosPublicados()) {
                todos.add(c);
            }
        }

        todos = todos.stream()
                .sorted(Comparator.comparingInt(Contenido::getValoraciones).reversed())
                .limit(5)
                .collect(Collectors.toList());

        String mensaje = todos.isEmpty() ? "No hay contenidos disponibles." :
                todos.stream().map(c -> c.getTitulo() + " - " + c.getValoraciones() + " valoraciones")
                        .collect(Collectors.joining("\n"));

        mostrarAlerta("Top 5 contenidos más valorados", mensaje);
    }

    private void mostrarTopConectados() {
        String mensaje = usuarios.stream()
                .sorted((u1, u2) -> Integer.compare(grafo.obtenerConexiones(u2).size(), grafo.obtenerConexiones(u1).size()))
                .limit(5)
                .map(u -> u.getUsername() + " - " + grafo.obtenerConexiones(u).size() + " conexiones")
                .collect(Collectors.joining("\n"));

        mostrarAlerta("Estudiantes con más conexiones", mensaje);
    }

    private void mostrarRutaMasCorta() {
        if (usuarios.size() < 2) {
            mostrarAlerta("Ruta más corta", "No hay suficientes usuarios conectados.");
            return;
        }
        List<User> ruta = grafo.caminoMasCorto(usuarios.get(0), usuarios.get(usuarios.size() - 1));
        String mensaje = ruta.isEmpty() ? "No hay camino." :
                ruta.stream().map(User::getUsername).collect(Collectors.joining(" -> "));
        mostrarAlerta("Ruta más corta entre " + usuarios.get(0).getUsername() + " y " + usuarios.get(usuarios.size() - 1).getUsername(), mensaje);
    }

    private void mostrarClusters() {
        Set<User> visitados = new HashSet<>();
        List<Set<User>> clusters = new ArrayList<>();

        for (User u : usuarios) {
            if (!visitados.contains(u)) {
                Set<User> cluster = new HashSet<>();
                dfs(u, cluster, visitados);
                clusters.add(cluster);
            }
        }

        String mensaje = clusters.isEmpty() ? "No hay clústeres." : "Se detectaron " + clusters.size() + " clúster(es).\n\n" +
                clusters.stream().map(set ->
                        set.stream().map(User::getUsername).collect(Collectors.joining(", "))
                ).collect(Collectors.joining("\n\n"));

        mostrarAlerta("Clústeres de afinidad", mensaje);
    }

    private void dfs(User actual, Set<User> cluster, Set<User> visitados) {
        visitados.add(actual);
        cluster.add(actual);
        for (User vecino : grafo.obtenerConexiones(actual)) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, cluster, visitados);
            }
        }
    }

    private void mostrarParticipacion() {
        String mensaje = usuarios.stream()
                .map(u -> u.getUsername() + ": " + contar(u.getContenidosPublicados()) + " contenidos")
                .collect(Collectors.joining("\n"));

        mostrarAlerta("Nivel de participación", mensaje);
    }

    private int contar(Iterable<?> iterable) {
        int count = 0;
        for (Object o : iterable) count++;
        return count;
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}