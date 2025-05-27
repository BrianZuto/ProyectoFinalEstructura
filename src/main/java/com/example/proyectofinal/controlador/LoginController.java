package com.example.proyectofinal.controlador;

import com.example.proyectofinal.modelo.User;
import com.example.proyectofinal.modelo.UserStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void iniciarSesion(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        User user = UserStore.autenticar(username, password);

        if (user == null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de inicio de sesión");
            alerta.setHeaderText(null);
            alerta.setContentText("Usuario o contraseña incorrectos.");
            alerta.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/MainTabbedView.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setUsuarioActual(user);

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Red Social - Bienvenido " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irARegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/RegisterView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}