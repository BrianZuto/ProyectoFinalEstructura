package com.example.proyectofinal.controlador;

import com.example.proyectofinal.modelo.User;
import com.example.proyectofinal.modelo.UserStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboRol;

    @FXML
    public void initialize() {
        comboRol.getItems().addAll("estudiante", "moderador");
    }

    @FXML
    public void registrarUsuario() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String rol = comboRol.getValue();

        if (UserStore.existeUsuario(username)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Registro fallido");
            alerta.setHeaderText(null);
            alerta.setContentText("Ya existe un usuario con ese nombre.");
            alerta.showAndWait();
            return;
        }

        User nuevoUsuario = new User(username, password, rol);
        UserStore.registrarUsuario(nuevoUsuario);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro exitoso");
        alerta.setHeaderText(null);
        alerta.setContentText("Usuario registrado correctamente. Ahora puedes iniciar sesión.");
        alerta.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}