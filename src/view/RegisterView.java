package view;

import controller.AuthController;
import controller.AyudaController;
import controller.ContenidoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class RegisterView extends JFrame {

    private JTextField nombreField, correoField, interesesField;
    private JPasswordField claveField;
    private JButton registrarBtn, volverBtn;
    private AuthController authController;
    private AyudaController ayudaController;
    private ContenidoController contenidoController;

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
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return boton;
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 10, 40));
        panel.setBackground(new Color(250, 250, 250));

        nombreField = new JTextField();
        correoField = new JTextField();
        claveField = new JPasswordField();
        interesesField = new JTextField();

        registrarBtn = crearBotonEstilizado("Registrarse", new Color(46, 204, 113));
        volverBtn = crearBotonEstilizado("Volver", new Color(52, 152, 219));

        panel.add(new JLabel("Nombre completo:"));
        panel.add(nombreField);

        panel.add(new JLabel("Correo electrónico:"));
        panel.add(correoField);

        panel.add(new JLabel("Contraseña:"));
        panel.add(claveField);

        panel.add(new JLabel("Intereses (separados por coma):"));
        panel.add(interesesField);

        panel.add(volverBtn);
        panel.add(registrarBtn);

        add(panel, BorderLayout.CENTER);

        registrarBtn.addActionListener(this::handleRegistro);
        volverBtn.addActionListener(e -> dispose());
    }

    private void handleRegistro(ActionEvent e) {
        String nombre = nombreField.getText().trim();
        String correo = correoField.getText().trim();
        String clave = new String(claveField.getPassword()).trim();
        String[] interesesArray = interesesField.getText().split(",");

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
}
