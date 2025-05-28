package view;

import controller.AuthController;
import controller.AyudaController;
import controller.ContenidoController;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;

public class RegisterView extends JFrame {

    private JTextField nombreField, correoField, interesesField;
    private JPasswordField claveField;
    private JButton registrarBtn, volverBtn;
    private AuthController authController;
    private AyudaController ayudaController;
    private ContenidoController contenidoController;

    // Colores modernos (mismos del LoginView)
    private static final Color PRIMARY_COLOR = new Color(74, 144, 226);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 37, 41);
    private static final Color PLACEHOLDER_COLOR = new Color(134, 142, 150);

    public RegisterView(AuthController authController, AyudaController ayudaController, ContenidoController contenidoController) {
        this.authController = authController;
        this.ayudaController = ayudaController;
        this.contenidoController = contenidoController;
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        aplicarEstiloGlobal();
        setTitle("Registro de nuevo estudiante");
        setSize(600, 650); // ✅ Tamaño aumentado para mejor diseño
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR); // ✅ Color moderno
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 15)); // ✅ Tamaño aumentado
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 15));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 15));
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); // ✅ Bordes redondeados
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); // ✅ Padding aumentado
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ✅ Cursor pointer
        
        // ✅ Efectos hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    private JTextField crearCampoModerno(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // ✅ Bordes redondeados
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15) // ✅ Padding interno
        ));
        field.setBackground(new Color(248, 249, 250));
        field.setForeground(TEXT_COLOR);
        field.setPreferredSize(new Dimension(0, 45)); // ✅ Altura fija
        
        // ✅ Placeholder funcional
        if (placeholder != null && !placeholder.isEmpty()) {
            configurarPlaceholder(field, placeholder);
        }
        
        return field;
    }

    private JPasswordField crearCampoPasswordModerno() {
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
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        field.setBackground(new Color(248, 249, 250));
        field.setForeground(TEXT_COLOR);
        field.setPreferredSize(new Dimension(0, 45));
        
        return field;
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

    private void inicializarComponentes() {
        // ✅ Panel principal con tarjeta centrada
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 30, 30, 30);
        
        // ✅ Tarjeta con diseño moderno
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(new RoundedBorder(20, new Color(0, 0, 0, 15))); // ✅ Sombra sutil
        cardPanel.setPreferredSize(new Dimension(480, 550));
        
        // ✅ Header con título
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        
        JLabel iconLabel = new JLabel("📝");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titulo = new JLabel("Crear Cuenta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(TEXT_COLOR);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("Únete a nuestra comunidad educativa");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(SECONDARY_COLOR);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titulo);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitulo);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 20)); // ✅ Espaciado aumentado
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // ✅ Padding aumentado
        formPanel.setBackground(CARD_COLOR);

        // Crear campos con diseño moderno
        nombreField = crearCampoModerno("Ingresa tu nombre completo");
        correoField = crearCampoModerno("ejemplo@correo.com");
        claveField = crearCampoPasswordModerno();
        interesesField = crearCampoModerno("matemáticas, programación, historia");

        //Crear botones con colores actualizados
        registrarBtn = crearBotonEstilizado("Registrarse", SUCCESS_COLOR); // ✅ Verde moderno
        volverBtn = crearBotonEstilizado("Volver", PRIMARY_COLOR); // ✅ Azul moderno

        //Labels con estilo moderno
        JLabel nombreLabel = new JLabel("Nombre completo:");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nombreLabel.setForeground(TEXT_COLOR);
        
        JLabel correoLabel = new JLabel("Correo electrónico:");
        correoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        correoLabel.setForeground(TEXT_COLOR);
        
        JLabel claveLabel = new JLabel("Contraseña:");
        claveLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        claveLabel.setForeground(TEXT_COLOR);
        
        JLabel interesesLabel = new JLabel("Intereses:");
        interesesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        interesesLabel.setForeground(TEXT_COLOR);

        //Agregar componentes al formulario
        formPanel.add(nombreLabel);
        formPanel.add(nombreField);
        formPanel.add(correoLabel);
        formPanel.add(correoField);
        formPanel.add(claveLabel);
        formPanel.add(claveField);
        formPanel.add(interesesLabel);
        formPanel.add(interesesField);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        buttonPanel.add(volverBtn);
        buttonPanel.add(registrarBtn);

       
        cardPanel.add(headerPanel, BorderLayout.NORTH);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(cardPanel, gbc);
        add(mainPanel, BorderLayout.CENTER);

        // Mantener la lógica original exactamente igual
        registrarBtn.addActionListener(this::handleRegistro);
        volverBtn.addActionListener(e -> dispose());
    }

    // LÓGICA ORIGINAL INTACTA 
    private void handleRegistro(ActionEvent e) {
        String nombre = nombreField.getText().trim();
        String correo = correoField.getText().trim();
        String clave = new String(claveField.getPassword()).trim();
        String[] interesesArray = interesesField.getText().split(",");

        // Limpiar placeholders antes de validar
        if (nombre.equals("Ingresa tu nombre completo")) nombre = "";
        if (correo.equals("ejemplo@correo.com")) correo = "";
        if (interesesField.getText().equals("matemáticas, programación, historia")) {
            interesesArray = new String[]{};
        }

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || interesesArray.length == 0) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LinkedList<String> intereses = new LinkedList<>();
        for (String interes : interesesArray) {
            if (!interes.trim().isEmpty()) intereses.add(interes.trim());
        }

        boolean registrado = authController.registrarEstudiante(nombre, correo, clave, intereses);
        if (registrado) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Correo ya registrado o error en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
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
            return new Insets(2, 2, 2, 2);
        }
    }
}
