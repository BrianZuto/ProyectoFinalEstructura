package view;

import controller.*;
import model.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PanelEstudianteView extends JFrame {

    private Estudiante estudiante;
    private AuthController authController;
    private ContenidoController contenidoController;
    private GrupoEstudioController grupoController;
    private AyudaController ayudaController;
    private JTextArea areaInicio;
    private JTextArea areaBuscar;
    private JTextArea areaSolicitudes; // Área de texto para mostrar las solicitudes
    private JButton cargarBtn;          // Botón para cargar las solicitudes


    public PanelEstudianteView(Estudiante estudiante, AuthController authController, AyudaController ayudaController, ContenidoController contenidoController) {
    this.estudiante = estudiante;
    this.authController = authController;
    this.ayudaController = ayudaController;
    this.contenidoController = contenidoController;
    this.grupoController = new GrupoEstudioController();
    configurarVentana();
    inicializarPestañas();
    setVisible(true);
}


    private void configurarVentana() {
        aplicarEstiloGlobal();
        setTitle("Panel del Estudiante - " + estudiante.getNombre());
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
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
        
        
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabs.addTab("Inicio", null, crearTabInicio(), "Explora los contenidos publicados");
        tabs.addTab("Publicar Contenido", null, crearTabPublicarContenido(), "Comparte nuevo material educativo");
        tabs.add("Buscar Contenido", crearTabBuscarContenido());
        tabs.addTab("Solicitar Ayuda", null, crearTabAyuda(), "Solicita asistencia académica");
        tabs.addTab("Sugerencias", null, crearTabSugerencias(), "Conecta con compañeros sugeridos");
        tabs.addTab("Grupos de Estudio", null, crearTabGrupos(), "Accede a tus grupos");
        tabs.addTab("Mensajería", null, crearTabMensajes(), "Comunícate con otros estudiantes");
        tabs.addTab("Valorar Contenido", null, crearTabValorar(), "Evalúa recursos educativos");
        tabs.addTab("Mis Contenidos", null, crearTabMisContenidos(), "Gestiona tus contenidos publicados");
        tabs.addTab("Ver Solicitudes", null, crearTabVerSolicitudes(), "Responder solicitudes de otros");


        


        JPanel panelSalir = new JPanel();
        JButton salirBtn = crearBotonEstilizado("Salir del sistema", new Color(231, 76, 60));
        salirBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        salirBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panelSalir.add(salirBtn);
        tabs.addTab("Salir", null, panelSalir, "Cerrar sesión");

        add(tabs);

        
        
    }

     private void cargarSolicitudes() {
        areaSolicitudes.setText("Solicitudes de ayuda registradas:\n\n"); // Limpia el área de texto
        List<SolicitudAyuda> solicitudes = ayudaController.verSolicitudes(); // Obtiene la lista de solicitudes
        
        if (solicitudes.isEmpty()) {
            areaSolicitudes.append("No hay solicitudes registradas.\n"); // Mensaje si no hay solicitudes
        } else {
            int i = 0;
            for (SolicitudAyuda s : solicitudes) {
                // Muestra información básica de la solicitud
                areaSolicitudes.append(i + ". " + s.getEstudiante().getNombre() +
                        " - Tema: " + s.getTema() + " | Urgencia: " + s.getUrgencia() + "\n");
                
                // Muestra respuestas si existen
                if (!s.getRespuestas().isEmpty()) {
                    areaSolicitudes.append("  Respuestas:\n");
                    for (Mensaje respuesta : s.getRespuestas()) {
                        areaSolicitudes.append("    De: " + respuesta.getEmisor() + 
                                               " | Mensaje: " + respuesta.getContenido() + "\n");
                    }
                } else {
                    areaSolicitudes.append("  No hay respuestas a esta solicitud.\n");
                }
                i++;
            }
        }
    }
    
    private JPanel crearTabInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton cargar = crearBotonEstilizado("Mostrar contenidos", new Color(52, 152, 219));

        cargar.addActionListener(e -> {
            area.setText("Contenidos publicados:\n\n");
            LinkedList<Contenido> contenidos = contenidoController.obtenerContenidosOrdenados();
            if (contenidos.isEmpty()) {
                area.append("No hay contenidos aún.\n");
            } else {
                int i = 1;
                for (Contenido c : contenidos) {
                    area.append(i++ + ". " + c.getTitulo() + " | Tema: " + c.getTema() + " | Autor: " + c.getAutor()
                            + " | Tipo: " + c.getTipo() + "\nDescripción: " + c.getDescripcion()
                            + "\nValoración promedio: " + String.format("%.2f", c.promedioValoraciones()) + "\n\n");
                }
            }
        });

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(cargar, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearTabPublicarContenido() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(250, 250, 250));

        JTextField tituloField = new JTextField();
        JTextField tipoField = new JTextField();
        JTextField temaField = new JTextField();
        JTextArea descArea = new JTextArea();
        JButton publicarBtn = crearBotonEstilizado("Publicar", new Color(46, 204, 113));

        panel.add(new JLabel("Título:")); panel.add(tituloField);
        panel.add(new JLabel("Tipo (video/pdf/etc):")); panel.add(tipoField);
        panel.add(new JLabel("Tema:")); panel.add(temaField);
        panel.add(new JLabel("Descripción:")); panel.add(new JScrollPane(descArea));
        panel.add(new JLabel()); panel.add(publicarBtn);

        publicarBtn.addActionListener(e -> {
            Contenido contenido = new Contenido(
                    tituloField.getText(), tipoField.getText(), descArea.getText(),
                    estudiante.getNombre(), temaField.getText()
            );
            contenidoController.publicarContenido(contenido);
            estudiante.getContenidosPublicados().add(contenido);
            JOptionPane.showMessageDialog(this, "Contenido publicado.");
            tituloField.setText(""); tipoField.setText(""); temaField.setText(""); descArea.setText("");
        });

        return panel;
    }

    private JPanel crearTabAyuda() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(250, 250, 250));

        JTextField temaField = new JTextField();
        JComboBox<String> urgenciaBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        JButton solicitarBtn = crearBotonEstilizado("Solicitar ayuda", new Color(241, 196, 15));

        panel.add(new JLabel("Tema en el que necesitas ayuda:"));
        panel.add(temaField);
        panel.add(new JLabel("Nivel de urgencia (1-5):"));
        panel.add(urgenciaBox);
        panel.add(solicitarBtn);

        solicitarBtn.addActionListener(e -> {
            String tema = temaField.getText().trim();
            if (tema.isBlank()) {
                JOptionPane.showMessageDialog(this, "El tema no puede estar vacío.");
                return;
            }
            int urgencia = Integer.parseInt((String) urgenciaBox.getSelectedItem());
            ayudaController.registrarSolicitud(estudiante, tema, urgencia);
            JOptionPane.showMessageDialog(this, "Solicitud de ayuda registrada.");
            temaField.setText("");
        });

        return panel;
    }

    private JPanel crearTabVerSolicitudes() {
    JPanel panel = new JPanel(new BorderLayout());
    JTextArea areaSolicitudes = new JTextArea();
    areaSolicitudes.setEditable(false);

    JTextField campoIndice = new JTextField();
    JTextArea campoRespuesta = new JTextArea(3, 30);
    JTextField campoFecha = new JTextField("2025-05-27");

    JButton cargarBtn = crearBotonEstilizado("Ver solicitudes", new Color(52, 152, 219));
    JButton responderBtn = crearBotonEstilizado("Responder solicitud", new Color(46, 204, 113));

    // Mostrar solicitudes
    cargarBtn.addActionListener(e -> {
        areaSolicitudes.setText("Solicitudes de ayuda registradas:\n\n");
        List<SolicitudAyuda> solicitudes = ayudaController.verSolicitudes();
        if (solicitudes.isEmpty()) {
            areaSolicitudes.append("No hay solicitudes registradas.\n");
        } else {
            int i = 0;
            for (SolicitudAyuda s : solicitudes) {
                areaSolicitudes.append(i + ". " + s.getEstudiante().getNombre() +
                        " - Tema: " + s.getTema() + " | Urgencia: " + s.getUrgencia() + "\n");
                i++;
            }
        }
    });

    // Responder solicitud
    // Dentro del método responderSolicitud en PanelEstudianteView
responderBtn.addActionListener(e -> {
    try {
        int index = Integer.parseInt(campoIndice.getText());
        String mensaje = campoRespuesta.getText();
        String fecha = campoFecha.getText();

        List<SolicitudAyuda> solicitudes = ayudaController.verSolicitudes();
        if (index < 0 || index >= solicitudes.size()) {
            JOptionPane.showMessageDialog(this, "Índice fuera de rango.");
            return;
        }

        SolicitudAyuda seleccionada = solicitudes.get(index);
        Estudiante receptor = seleccionada.getEstudiante();

        if (receptor.equals(estudiante)) {
            JOptionPane.showMessageDialog(this, "No puedes responder tu propia solicitud.");
            return;
        }

        // Enviar mensaje al estudiante solicitante
        MensajeController mensajeController = new MensajeController();
        mensajeController.enviarMensaje(estudiante, receptor, mensaje, fecha);

        // Agregar respuesta a la solicitud
        seleccionada.agregarRespuesta(new Mensaje(estudiante.getNombre(), receptor.getNombre(), mensaje, fecha));

        JOptionPane.showMessageDialog(this, "Respuesta enviada correctamente.");
        campoRespuesta.setText("");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al responder: " + ex.getMessage());
    }
});


    JPanel respuestas = new JPanel(new GridLayout(6, 1, 5, 5));
    respuestas.setBorder(BorderFactory.createTitledBorder("Responder solicitud"));
    respuestas.add(new JLabel("Índice de la solicitud:"));
    respuestas.add(campoIndice);
    respuestas.add(new JLabel("Mensaje de respuesta:"));
    respuestas.add(new JScrollPane(campoRespuesta));
    respuestas.add(new JLabel("Fecha (YYYY-MM-DD):"));
    respuestas.add(campoFecha);

    JPanel botones = new JPanel();
    botones.add(cargarBtn);
    botones.add(responderBtn);

    panel.add(new JScrollPane(areaSolicitudes), BorderLayout.CENTER);
    panel.add(respuestas, BorderLayout.SOUTH);
    panel.add(botones, BorderLayout.NORTH);

    return panel;
}


    private JPanel crearTabSugerencias() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton generar = crearBotonEstilizado("Ver sugerencias", new Color(52, 152, 219));

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(generar, BorderLayout.SOUTH);

        generar.addActionListener(e -> {
            controller.GrafoController grafoController = new controller.GrafoController();
            LinkedList<Estudiante> todos = new LinkedList<>();
            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) todos.add(est);
            }

            grafoController.construirGrafoDesdeEstudiantes(todos);
            var sugeridos = grafoController.sugerencias(estudiante);

            area.setText("Sugerencias de compañeros:\n\n");
            if (sugeridos.isEmpty()) {
                area.append("No se encontraron estudiantes relacionados aún.\n");
            } else {
                for (Estudiante s : sugeridos) {
                    area.append("- " + s.getNombre() + " (" + s.getCorreo() + ")\n");
                }
            }
        });

        return panel;
    }

    private JPanel crearTabGrupos() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);

        JButton cargarBtn = crearBotonEstilizado("Ver mis grupos", new Color(39, 174, 96));
        JButton generarBtn = crearBotonEstilizado("Generar automáticamente", new Color(39, 89, 182));

        cargarBtn.addActionListener(e -> {
            area.setText("Grupos actuales:\n");
            for (GrupoEstudio g : estudiante.getGrupos()) {
                area.append("- Tema: " + g.getTema() + ", Participantes: " + g.getParticipantes().size() + "\n");
            }
        });

        generarBtn.addActionListener(e -> {
            LinkedList<Estudiante> todos = new LinkedList<>();
            for (Usuario u : authController.getUsuariosRegistrados()) {
                if (u instanceof Estudiante est) todos.add(est);
            }
            grupoController.generarGruposPorIntereses(todos);
            JOptionPane.showMessageDialog(this, "Grupos generados automáticamente.");
        });

        JPanel botones = new JPanel();
        botones.add(cargarBtn);
        botones.add(generarBtn);

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearTabBuscarContenido() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel filtroPanel = new JPanel(new GridLayout(2, 4, 10, 10));

    JTextField campoTema = new JTextField();
    JTextField campoAutor = new JTextField();
    JTextField campoTipo = new JTextField();
    JButton buscarBtn = crearBotonEstilizado("Buscar", new Color(52, 152, 219));

    filtroPanel.add(new JLabel("Tema:")); filtroPanel.add(campoTema);
    filtroPanel.add(new JLabel("Autor:")); filtroPanel.add(campoAutor);
    filtroPanel.add(new JLabel("Tipo:")); filtroPanel.add(campoTipo);
    filtroPanel.add(new JLabel()); filtroPanel.add(buscarBtn);

    JTextArea resultados = new JTextArea();
    resultados.setEditable(false);

    buscarBtn.addActionListener(e -> {
        String tema = campoTema.getText().trim().toLowerCase();
        String autor = campoAutor.getText().trim().toLowerCase();
        String tipo = campoTipo.getText().trim().toLowerCase();

        LinkedList<Contenido> lista = contenidoController.obtenerContenidosOrdenados();
        resultados.setText("Resultados de búsqueda:\n\n");

        boolean huboResultados = false;

        for (Contenido c : lista) {
            boolean coincideTema = tema.isBlank() || c.getTema().toLowerCase().contains(tema);
            boolean coincideAutor = autor.isBlank() || c.getAutor().toLowerCase().contains(autor);
            boolean coincideTipo = tipo.isBlank() || c.getTipo().toLowerCase().contains(tipo);

            if (coincideTema && coincideAutor && coincideTipo) {
                resultados.append("Título: " + c.getTitulo() +
                        "\nTema: " + c.getTema() +
                        "\nAutor: " + c.getAutor() +
                        "\nTipo: " + c.getTipo() +
                        "\nDescripción: " + c.getDescripcion() +
                        "\nValoración promedio: " + String.format("%.2f", c.promedioValoraciones()) + "\n\n");
            }
        }

        
    });

    panel.add(filtroPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(resultados), BorderLayout.CENTER);
    return panel;
    }

    private JPanel crearTabMensajes() {
    JPanel panel = new JPanel(new BorderLayout());
    JTextArea areaMensajes = new JTextArea();
    areaMensajes.setEditable(false);

    JComboBox<String> destinatariosBox = new JComboBox<>();
    JTextField campoFecha = new JTextField("2025-05-27");
    JTextArea campoMensaje = new JTextArea(3, 30);
    JButton enviarBtn = crearBotonEstilizado("Enviar mensaje", new Color(39, 174, 96));
    JButton verRecibidosBtn = crearBotonEstilizado("Ver recibidos", new Color(41, 128, 185));
    JButton verEnviadosBtn = crearBotonEstilizado("Ver enviados", new Color(155, 89, 182));
    JButton actualizarBtn = crearBotonEstilizado("Actualizar destinatarios", new Color(52, 73, 94));

    JPanel envioPanel = new JPanel(new GridLayout(6, 1, 5, 5));
    envioPanel.setBorder(BorderFactory.createTitledBorder("Nuevo mensaje"));
    envioPanel.add(new JLabel("Destinatario (correo):"));
    envioPanel.add(destinatariosBox);
    envioPanel.add(new JLabel("Fecha (YYYY-MM-DD):"));
    envioPanel.add(campoFecha);
    envioPanel.add(new JLabel("Mensaje:"));
    envioPanel.add(new JScrollPane(campoMensaje));

    MensajeController mensajeController = new MensajeController();

    // Acción para enviar mensaje
    enviarBtn.addActionListener(e -> {
        String correoDestino = (String) destinatariosBox.getSelectedItem();
        String texto = campoMensaje.getText();
        String fecha = campoFecha.getText();

        if (correoDestino == null || texto.isBlank() || fecha.isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.");
            return;
        }

        Estudiante receptor = null;
        for (Usuario u : authController.getUsuariosActivos()) {
            if (u instanceof Estudiante est && est.getCorreo().equals(correoDestino)) {
                receptor = est;
                break;
            }
        }

        if (receptor != null) {
            mensajeController.enviarMensaje(estudiante, receptor, texto, fecha);
            JOptionPane.showMessageDialog(this, "Mensaje enviado correctamente.");
            campoMensaje.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
        }
    });

    // Acción para cargar mensajes recibidos
    verRecibidosBtn.addActionListener(e -> {
        areaMensajes.setText("Mensajes recibidos:\n\n");
        var lista = mensajeController.obtenerMensajes(estudiante);
        if (lista.isEmpty()) {
            areaMensajes.append("No hay mensajes aún.\n");
        } else {
            for (Mensaje m : lista) {
                String notificacion = m.isNuevo() ? " (NUEVO)" : "";
                areaMensajes.append("De: " + m.getEmisor() + " | Fecha: " + m.getFecha() + notificacion + "\n");
                areaMensajes.append("Mensaje: " + m.getContenido() + "\n\n");
                m.setNuevo(false); // marcar como leído
            }
        }
    });

    // Acción para ver mensajes enviados
    verEnviadosBtn.addActionListener(e -> {
        areaMensajes.setText("Mensajes enviados:\n\n");
        var lista = estudiante.getMensajesEnviados();
        if (lista.isEmpty()) {
            areaMensajes.append("No has enviado ningún mensaje aún.\n");
        } else {
            for (Mensaje m : lista) {
                areaMensajes.append("Para: " + m.getReceptor() + " | Fecha: " + m.getFecha() + "\n");
                areaMensajes.append("Mensaje: " + m.getContenido() + "\n\n");
            }
        }
    });

    // Acción para actualizar el combo de destinatarios
    actualizarBtn.addActionListener(e -> {
        destinatariosBox.removeAllItems();
        for (Usuario u : authController.getUsuariosActivos()) {
            if (u instanceof Estudiante est && !est.getCorreo().equals(estudiante.getCorreo())) {
                destinatariosBox.addItem(est.getCorreo());
            }
        }
        
    });

    JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    acciones.add(actualizarBtn);
    acciones.add(enviarBtn);
    acciones.add(verRecibidosBtn);
    acciones.add(verEnviadosBtn);

    JPanel seccionSuperior = new JPanel(new BorderLayout());
    seccionSuperior.add(envioPanel, BorderLayout.CENTER);
    seccionSuperior.add(acciones, BorderLayout.SOUTH);

    panel.add(seccionSuperior, BorderLayout.NORTH);
    panel.add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

    actualizarBtn.doClick();
    return panel;
}


    private JPanel crearTabMisContenidos() {
    JPanel panel = new JPanel(new BorderLayout());
    JTextArea area = new JTextArea();
    area.setEditable(false);

    JButton cargarBtn = crearBotonEstilizado("Ver mis contenidos", new Color(52, 152, 219));
    JButton editarBtn = crearBotonEstilizado("Editar contenido", new Color(243, 156, 18));
    JButton eliminarBtn = crearBotonEstilizado("Eliminar contenido", new Color(192, 57, 43));

    JTextField campoIndice = new JTextField();
    LinkedList<Contenido> contenidosPropios = estudiante.getContenidosPublicados();

    cargarBtn.addActionListener(e -> {
        area.setText("Contenidos publicados por ti:\n\n");
        for (int i = 0; i < contenidosPropios.size(); i++) {
            Contenido c = contenidosPropios.get(i);
            if (!c.isEliminado()) {
                area.append(i + ". " + c.getTitulo() + " (" + c.getTema() + ") - " + c.getDescripcion() + "\n");
            }
        }
    });

    editarBtn.addActionListener(e -> {
        try {
            int index = Integer.parseInt(campoIndice.getText());
            Contenido c = contenidosPropios.get(index);
            if (c.isEliminado()) {
                JOptionPane.showMessageDialog(this, "Este contenido ya fue eliminado.");
                return;
            }
            new EditarContenidoView(c); // abre la ventana de edición
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Índice inválido o contenido no disponible.");
        }
    });

    eliminarBtn.addActionListener(e -> {
        try {
            int index = Integer.parseInt(campoIndice.getText());
            Contenido c = contenidosPropios.get(index);
            if (c.isEliminado()) {
                JOptionPane.showMessageDialog(this, "Este contenido ya está eliminado.");
                return;
            }
            c.setEliminado(true);
            JOptionPane.showMessageDialog(this, "Contenido eliminado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Índice inválido.");
        }
    });

    JPanel acciones = new JPanel(new GridLayout(2, 2, 10, 10));
    acciones.add(new JLabel("Índice del contenido:"));
    acciones.add(campoIndice);
    acciones.add(editarBtn);
    acciones.add(eliminarBtn);

    JPanel superior = new JPanel(new BorderLayout());
    superior.add(cargarBtn, BorderLayout.NORTH);
    superior.add(acciones, BorderLayout.CENTER);

    panel.add(new JScrollPane(area), BorderLayout.CENTER);
    panel.add(superior, BorderLayout.SOUTH);
    return panel;
}



    private JPanel crearTabValorar() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea("Lista de contenidos...\nHaz clic en uno para valorarlo.");
        area.setEditable(false);
        JButton cargar = crearBotonEstilizado("Cargar contenidos", new Color(52, 152, 219));

        JComboBox<Integer> valorCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JButton valorarBtn = crearBotonEstilizado("Valorar contenido", new Color(41, 156, 18));
        JTextField campoIndice = new JTextField();
        LinkedList<Contenido> listaTemp = new LinkedList<>();

        cargar.addActionListener(e -> {
            area.setText("Contenidos disponibles:\n");
            listaTemp.clear();
            contenidoController.getArbol().inordenToList(listaTemp);
            for (int i = 0; i < listaTemp.size(); i++) {
                Contenido c = listaTemp.get(i);
                area.append(i + ". " + c.getTitulo() + " (" + c.getTema() + ") - promedio: " + c.promedioValoraciones() + "\n");
            }
        });

        valorarBtn.addActionListener(e -> {
            try {
                int indice = Integer.parseInt(campoIndice.getText());
                int valor = (Integer) valorCombo.getSelectedItem();
                Contenido c = listaTemp.get(indice);
                c.agregarValoracion(valor);
                estudiante.getHistorialValoraciones().agregarContenido(c);
                JOptionPane.showMessageDialog(this, "Valoración registrada.");
                campoIndice.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Selecciona un contenido válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel abajo = new JPanel(new GridLayout(3, 2, 5, 5));
        abajo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        abajo.add(new JLabel("Índice del contenido:"));
        abajo.add(campoIndice);
        abajo.add(new JLabel("Valor (1-5):"));
        abajo.add(valorCombo);
        abajo.add(new JLabel());
        abajo.add(valorarBtn);

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(cargar, BorderLayout.NORTH);
        panel.add(abajo, BorderLayout.SOUTH);
        return panel;
    }
}
