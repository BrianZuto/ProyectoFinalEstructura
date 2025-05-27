package view;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelModeradorView extends JFrame {

    private Moderador moderador;
    private AuthController authController;
    private ContenidoController contenidoController;
    private AyudaController ayudaController;

    public PanelModeradorView(Moderador moderador, AuthController authController, AyudaController ayudaController, ContenidoController contenidoController) {
    this.moderador = moderador;
    this.authController = authController;
    this.ayudaController = ayudaController;
    this.contenidoController = contenidoController;
    configurarVentana();
    inicializarPestañas();
    setVisible(true);
}


    private void configurarVentana() {
        aplicarEstiloGlobal();
        setTitle("Panel del Moderador - " + moderador.getNombre());
        setSize(850, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return boton;
    }

    private void inicializarPestañas() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Usuarios", crearTabUsuarios());
        tabs.add("Reportes", crearTabReportes());
        tabs.add("Grafo de Afinidad", crearTabGrafo());
        tabs.add("Ruta más corta", crearTabRutaMasCorta());
        tabs.add("Clústeres", crearTabClusters());
        tabs.add("Solicitudes de Ayuda", crearTabAyuda());
        tabs.add("Participación", crearTabParticipacion());

        JPanel salirPanel = new JPanel();
        JButton salirBtn = crearBotonEstilizado("Salir del sistema", new Color(231, 76, 60));
        salirBtn.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        salirPanel.add(salirBtn);
        tabs.add("Salir", salirPanel);

        add(tabs);
    }

    private JPanel crearTabUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton cargarBtn = crearBotonEstilizado("Ver usuarios registrados", new Color(52, 152, 219));

        cargarBtn.addActionListener(e -> {
            area.setText("Usuarios registrados:\n\n");
            for (Usuario u : authController.getUsuariosRegistrados()) {
                area.append("- " + u.getNombre() + " (" + u.getCorreo() + ")\n");
            }
        });

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(cargarBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearTabReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);

        JButton btnValorados = crearBotonEstilizado("Contenidos más valorados", new Color(241, 196, 15));
        JButton btnConectados = crearBotonEstilizado("Estudiantes con más conexiones", new Color(155, 89, 182));

        btnValorados.addActionListener(e -> {
            area.setText("Top contenidos más valorados:\n\n");
            var lista = contenidoController.obtenerContenidosOrdenados();
            lista.sort((a, b) -> Double.compare(b.promedioValoraciones(), a.promedioValoraciones()));
            for (int i = 0; i < Math.min(10, lista.size()); i++) {
                var c = lista.get(i);
                area.append((i + 1) + ". " + c.getTitulo() + " - Tema: " + c.getTema() +
                        " - Promedio: " + String.format("%.2f", c.promedioValoraciones()) + "\n");
            }
        });

        btnConectados.addActionListener(e -> {
            area.setText("Estudiantes con más conexiones:\n\n");
            LinkedList<Estudiante> estudiantes = new LinkedList<>();
            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) estudiantes.add(est);
            }
            GrafoController g = new GrafoController();
            g.construirGrafoDesdeEstudiantes(estudiantes);
            estudiantes.sort((a, b) -> Integer.compare(
                    g.getVecinos(b).size(), g.getVecinos(a).size()));

            for (int i = 0; i < Math.min(10, estudiantes.size()); i++) {
                var est = estudiantes.get(i);
                int conexiones = g.getVecinos(est).size();
                area.append((i + 1) + ". " + est.getNombre() + " (" + est.getCorreo() + ") - Conexiones: " + conexiones + "\n");
            }
        });

        JPanel botones = new JPanel();
        botones.add(btnValorados);
        botones.add(btnConectados);
        panel.add(botones, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabAyuda() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton cargarBtn = crearBotonEstilizado("Ver solicitudes", new Color(52, 152, 219));
        JButton atenderBtn = crearBotonEstilizado("Atender siguiente", new Color(39, 174, 96));

        cargarBtn.addActionListener(e -> {
            area.setText("Solicitudes de ayuda:\n\n");
            var lista = ayudaController.verSolicitudes();
            if (lista.isEmpty()) {
                area.append("No hay solicitudes pendientes.\n");
            } else {
                int i = 1;
                for (var s : lista) {
                    area.append(i++ + ". Estudiante: " + s.getEstudiante().getNombre() +
                            " | Tema: " + s.getTema() +
                            " | Urgencia: " + s.getUrgencia() + "\n");
                }
            }
        });

        atenderBtn.addActionListener(e -> {
            var siguiente = ayudaController.atenderSiguiente();
            if (siguiente != null) {
                JOptionPane.showMessageDialog(this, "Solicitud atendida:\n" +
                        "Estudiante: " + siguiente.getEstudiante().getNombre() +
                        "\nTema: " + siguiente.getTema());
            } else {
                JOptionPane.showMessageDialog(this, "No hay solicitudes por atender.");
            }
        });

        JPanel acciones = new JPanel();
        acciones.add(cargarBtn);
        acciones.add(atenderBtn);
        panel.add(acciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabParticipacion() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton cargarBtn = crearBotonEstilizado("Ver participación", new Color(52, 152, 219));

        cargarBtn.addActionListener(e -> {
            area.setText("Nivel de participación por estudiante:\n\n");
            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) {
                    int publicaciones = est.getContenidosPublicados().size();
                    int valoraciones = est.getHistorialValoraciones().getContenidosValorados().size();
                    int mensajes = est.getMensajesRecibidos().size();
                    int ayudas = (int) ayudaController.verSolicitudes().stream().filter(s -> s.getEstudiante().equals(est)).count();

                    area.append("Nombre: " + est.getNombre() + "\n");
                    area.append("- Publicaciones: " + publicaciones + "\n");
                    area.append("- Valoraciones realizadas: " + valoraciones + "\n");
                    area.append("- Mensajes recibidos: " + mensajes + "\n");
                    area.append("- Solicitudes de ayuda: " + ayudas + "\n\n");
                }
            }
        });

        panel.add(cargarBtn, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabGrafo() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("[En construcción] Aquí se visualizará el grafo de afinidad entre estudiantes."));
        return panel;
    }

    private JPanel crearTabRutaMasCorta() {
        JPanel panel = new JPanel(new BorderLayout());
        JComboBox<String> origenBox = new JComboBox<>();
        JComboBox<String> destinoBox = new JComboBox<>();
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        for (Usuario u : authController.getUsuariosRegistrados()) {
            if (u instanceof Estudiante est) {
                origenBox.addItem(est.getCorreo());
                destinoBox.addItem(est.getCorreo());
            }
        }

        JButton calcularBtn = crearBotonEstilizado("Calcular ruta", new Color(52, 73, 94));
        calcularBtn.addActionListener(e -> {
            String correoOrigen = (String) origenBox.getSelectedItem();
            String correoDestino = (String) destinoBox.getSelectedItem();

            if (correoOrigen.equals(correoDestino)) {
                resultado.setText("Seleccione dos estudiantes diferentes.");
                return;
            }

            Estudiante origen = null, destino = null;
            LinkedList<Estudiante> todos = new LinkedList<>();

            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) {
                    todos.add(est);
                    if (est.getCorreo().equals(correoOrigen)) origen = est;
                    if (est.getCorreo().equals(correoDestino)) destino = est;
                }
            }

            GrafoController g = new GrafoController();
            g.construirGrafoDesdeEstudiantes(todos);
            var camino = g.rutaMasCorta(origen, destino);

            if (camino == null || camino.isEmpty()) {
                resultado.setText("No hay ruta entre los estudiantes.");
            } else {
                StringBuilder sb = new StringBuilder("Ruta más corta:\n");
                for (Estudiante est : camino) {
                    sb.append("-> ").append(est.getNombre()).append(" (" + est.getCorreo() + ")\n");
                }
                resultado.setText(sb.toString());
            }
        });

        JPanel selecciones = new JPanel();
        selecciones.add(new JLabel("Origen:")); selecciones.add(origenBox);
        selecciones.add(new JLabel("Destino:")); selecciones.add(destinoBox);
        selecciones.add(calcularBtn);

        panel.add(selecciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultado), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabClusters() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton btnDetectar = crearBotonEstilizado("Detectar comunidades", new Color(41, 128, 185));

        btnDetectar.addActionListener(e -> {
            area.setText("");
            List<Estudiante> estudiantes = new ArrayList<>();
            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) estudiantes.add(est);
            }

            GrafoController g = new GrafoController();
            g.construirGrafoDesdeEstudiantes(estudiantes);
            List<List<Estudiante>> clusters = g.detectarClusters();

            int i = 1;
            for (List<Estudiante> grupo : clusters) {
                area.append("Clúster " + i++ + ":\n");
                for (Estudiante est : grupo) {
                    area.append("- " + est.getNombre() + " (" + est.getCorreo() + ")\n");
                }
                area.append("\n");
            }
        });

        panel.add(btnDetectar, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }
}
