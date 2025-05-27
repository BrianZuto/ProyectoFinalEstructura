package com.example.proyectofinal.controlador;

import com.example.proyectofinal.modelo.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabInicio, tabEstudiante, tabGrupos, tabMensajes, tabModerador;

    @FXML
    private InicioController inicioViewController; // inyectado automáticamente desde fx:include fx:id="inicioView"

    private User usuarioActual;

    public void setUsuarioActual(User user) {
        this.usuarioActual = user;
        aplicarRol();
        cargarPerfilEnInicio();
    }

    private void aplicarRol() {
        if (usuarioActual != null) {
            boolean esEstudiante = usuarioActual.getRol().equalsIgnoreCase("estudiante");
            tabModerador.setDisable(esEstudiante);
            tabEstudiante.setDisable(!esEstudiante);
            tabGrupos.setDisable(!esEstudiante);
            tabMensajes.setDisable(!esEstudiante);
        }
    }

    private void cargarPerfilEnInicio() {
        if (inicioViewController != null && usuarioActual != null) {
            String perfil = "Usuario: " + usuarioActual.getUsername() + "\nRol: " + usuarioActual.getRol();
            inicioViewController.mostrarPerfil(perfil);
        }
    }

    @FXML
    public void initialize() {
        if (usuarioActual == null) {
            usuarioActual = new User("juan", "123", "estudiante");
            aplicarRol();
            cargarPerfilEnInicio();
        }
    }

    public void mostrarMensaje(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}
