package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button goToRegisterButton;  // Este es el botón para navegar al registro

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateUser(username, password)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Success");
            alert.setContentText("Login Successful!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid credentials");
            alert.setContentText("Please check your username and password.");
            alert.showAndWait();
        }
    }

    private boolean validateUser(String username, String password) {
        // Aquí validas el usuario con el archivo o base de datos
        return true;
    }

    // Método para navegar al formulario de registro
    @FXML
    public void goToRegister() {
        try {
            // Cambiar la ruta al archivo FXML, asegurándote de que esté correcta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/register.fxml"));
            Stage stage = new Stage();  // Crear un nuevo escenario para la vista de registro
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Register");
            stage.show();

            // Cerrar la ventana actual (opcional)
            Stage currentStage = (Stage) goToRegisterButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Mostrar mensaje de error si no se pudo cargar la vista
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de registro");
            alert.setContentText("Asegúrate de que la ruta al archivo FXML sea correcta.");
            alert.showAndWait();
        }
    }

}