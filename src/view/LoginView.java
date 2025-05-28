package view;

import controller.AuthController;
import controller.AyudaController;
import controller.ContenidoController;
import model.Usuario;
import model.Estudiante;
import model.Moderador;
import model.Contenido;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.LinkedList;

public class LoginView extends JFrame {

    private JTextField correoField;
    private JPasswordField contraseñaField;
    private JButton loginBtn, registerBtn;
    private AuthController authController;
    private AyudaController ayudaController;
    private ContenidoController contenidoController;

    // Colores modernos
    private static final Color PRIMARY_COLOR = new Color(74, 144, 226);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 37, 41);
    private static final Color PLACEHOLDER_COLOR = new Color(134, 142, 150);

    public LoginView(AuthController authController, AyudaController ayudaController, ContenidoController contenidoController) {
        this.authController = authController;
        this.ayudaController = ayudaController;
        this.contenidoController = contenidoController;
        configurarVentana();
        inicializarComponentes();
        this.setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Red Social Educativa - Iniciar Sesión");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void inicializarComponentes() {
        // Panel principal con tarjeta centrada
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(new RoundedBorder(30, CARD_COLOR));
        cardPanel.setPreferredSize(new Dimension(400, 500));

        // Header con logo/icono
        JPanel headerPanel = crearHeader();
        
        // Panel de formulario
        JPanel formPanel = crearFormulario();
        
        // Panel de botones
        JPanel buttonPanel = crearBotones();

        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(headerPanel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(formPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(buttonPanel);
        cardPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(cardPanel);
        add(mainPanel, BorderLayout.CENTER);

        configurarEventos();
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        // Icono/Logo
        JLabel iconLabel = new JLabel("🎓");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(TEXT_COLOR);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel subtitulo = new JLabel("Accede a tu cuenta estudiantil");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(SECONDARY_COLOR);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titulo);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitulo);

        return headerPanel;
    }

   private JPanel crearFormulario() {
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBackground(CARD_COLOR);
    formPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

    // ✅ Labels alineados a la izquierda
    JLabel correoLabel = new JLabel("Correo electrónico");
    correoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    correoLabel.setForeground(TEXT_COLOR);
    correoLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // ⬅️ IZQUIERDA

    correoField = crearCampoModerno("usuario@ejemplo.com");
    
    JLabel contraseñaLabel = new JLabel("Contraseña");
    contraseñaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    contraseñaLabel.setForeground(TEXT_COLOR);
    contraseñaLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // ⬅️ IZQUIERDA

    contraseñaField = crearCampoContraseñaModerno("Ingresa tu contraseña");

    formPanel.add(correoLabel);
    formPanel.add(Box.createVerticalStrut(8));
    formPanel.add(correoField);
    formPanel.add(Box.createVerticalStrut(22));
    formPanel.add(contraseñaLabel);
    formPanel.add(Box.createVerticalStrut(8));
    formPanel.add(contraseñaField);

    return formPanel;
}


    private JPanel crearBotones() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        loginBtn = crearBotonModerno("Iniciar Sesión", PRIMARY_COLOR, true);
        registerBtn = crearBotonModerno("Crear Cuenta", CARD_COLOR, false);
        JButton datosPruebaBtn = crearBotonModerno("Cargar Datos de Prueba", WARNING_COLOR, true);

        // Configurar eventos de los botones (manteniendo la lógica original)
        loginBtn.addActionListener(this::handleLogin);
        registerBtn.addActionListener(e -> new RegisterView(authController, ayudaController, contenidoController));
        
        datosPruebaBtn.addActionListener(e -> {
            LinkedList<String> intereses1 = new LinkedList<>(Arrays.asList("matemáticas", "programación"));
            LinkedList<String> intereses2 = new LinkedList<>(Arrays.asList("historia", "filosofía"));
            LinkedList<String> intereses3 = new LinkedList<>(Arrays.asList("programación", "base de datos"));
            LinkedList<String> intereses4 = new LinkedList<>(Arrays.asList("redes", "seguridad"));
            LinkedList<String> intereses5 = new LinkedList<>(Arrays.asList("inteligencia artificial", "ética"));

            authController.registrarEstudiante("Juan Pérez", "juan@correo.com", "123", intereses1);
            authController.registrarEstudiante("Ana Gómez", "ana@correo.com", "123", intereses2);
            authController.registrarEstudiante("Carlos Díaz", "carlos@correo.com", "123", intereses3);
            authController.registrarEstudiante("Laura Ruiz", "laura@correo.com", "123", intereses4);
            authController.registrarEstudiante("Luis Rojas", "luis@correo.com", "123", intereses5);

            contenidoController.publicarContenido(new Contenido("Álgebra Lineal", "pdf", "Guía práctica", "Juan Pérez", "matemáticas"));
            contenidoController.publicarContenido(new Contenido("Redes CISCO", "video", "Curso básico", "Laura Ruiz", "redes"));
            contenidoController.publicarContenido(new Contenido("Historia Moderna", "pdf", "Resumen académico", "Ana Gómez", "historia"));

            mostrarMensajeExito("Datos de prueba cargados correctamente.");
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(registerBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(datosPruebaBtn);

        return buttonPanel;
    }

    private JTextField crearCampoModerno(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(new Color(248, 249, 250));
        field.setForeground(TEXT_COLOR);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setPreferredSize(new Dimension(320, 45));
        
        configurarPlaceholder(field, placeholder);
        return field;
    }

    private JPasswordField crearCampoContraseñaModerno(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(new Color(248, 249, 250));
        field.setForeground(TEXT_COLOR);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setPreferredSize(new Dimension(320, 45));
        
        return field;
    }

    private JButton crearBotonModerno(String texto, Color color, boolean filled) {
        JButton button = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (filled) {
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                } else {
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.setColor(PRIMARY_COLOR);
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                }
                
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        if (filled) {
            button.setBackground(color);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(CARD_COLOR);
            button.setForeground(PRIMARY_COLOR);
        }
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(320, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Efectos hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (filled) {
                    button.setBackground(color.darker());
                } else {
                    button.setBackground(new Color(248, 249, 250));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (filled) {
                    button.setBackground(color);
                } else {
                    button.setBackground(CARD_COLOR);
                }
            }
        });
        
        return button;
    }

    private void configurarPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(PLACEHOLDER_COLOR);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
    }

    private void configurarEventos() {
        // Permitir login con Enter
        correoField.addActionListener(e -> contraseñaField.requestFocus());
        contraseñaField.addActionListener(this::handleLogin);
    }

    private void mostrarMensajeExito(String mensaje) {
        JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Éxito");
        dialog.setVisible(true);
    }

    // Mantener la lógica original del login
    private void handleLogin(ActionEvent e) {
        String correo = correoField.getText();
        if (correo.equals("usuario@ejemplo.com")) correo = "";
        
        String clave = new String(contraseñaField.getPassword());

        Usuario u = authController.login(correo, clave);

        if (u != null) {
            authController.registrarSesionActiva(u);
            mostrarMensajeExito("¡Bienvenido/a, " + u.getNombre() + "!");

            correoField.setText("usuario@ejemplo.com");
            correoField.setForeground(PLACEHOLDER_COLOR);
            contraseñaField.setText("");

            if (u instanceof Estudiante est) {
                new PanelEstudianteView(est, authController, ayudaController, contenidoController);
            } else {
                new PanelModeradorView((Moderador) u, authController, ayudaController, contenidoController);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clase para bordes redondeados
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }
}
