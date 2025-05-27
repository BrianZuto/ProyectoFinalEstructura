package view;

import controller.AuthController;
import controller.AyudaController;
import controller.ContenidoController;
import model.Usuario;
import model.Estudiante;
import model.Moderador;
import model.Contenido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;

public class LoginView extends JFrame {

    private JTextField correoField;
    private JPasswordField contraseñaField;
    private JButton loginBtn, registerBtn;
    private AuthController authController;
    private AyudaController ayudaController;
    private ContenidoController contenidoController;

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
        setSize(420, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 10, 40));
        panel.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(44, 62, 80));
        add(titulo, BorderLayout.NORTH);

        correoField = new JTextField();
        contraseñaField = new JPasswordField();

        estilizarCampo(correoField);
        estilizarCampo(contraseñaField);

        panel.add(new JLabel("Correo electrónico:"));
        panel.add(correoField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(contraseñaField);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(new Color(245, 245, 245));

        loginBtn = crearBoton("Iniciar sesión", new Color(52, 152, 219));
        registerBtn = crearBoton("Registrarse", new Color(46, 204, 113));
        JButton datosPruebaBtn = crearBoton("Cargar Datos de Prueba", new Color(241, 196, 15));

        botones.add(loginBtn);
        botones.add(registerBtn);
        botones.add(datosPruebaBtn);

        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

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

            JOptionPane.showMessageDialog(this, "Datos de prueba cargados correctamente.");
        });
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(130, 35));
        return boton;
    }

    private void handleLogin(ActionEvent e) {
        String correo = correoField.getText();
        String clave = new String(contraseñaField.getPassword());

        Usuario u = authController.login(correo, clave);

        if (u != null) {
            authController.registrarSesionActiva(u); // Registrar sesión activa

            JOptionPane.showMessageDialog(this, "¡Bienvenido/a, " + u.getNombre() + "!");

            // Limpiar campos después de login exitoso
            correoField.setText("");
            contraseñaField.setText("");

            if (u instanceof Estudiante est) {
                new PanelEstudianteView(est, authController, ayudaController, contenidoController);
            } else {
                new PanelModeradorView((Moderador) u, authController, ayudaController, contenidoController);
            }

            // No se cierra la ventana de login

        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);

            
        }
    }
}
