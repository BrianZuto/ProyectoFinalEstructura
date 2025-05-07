package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.proyectofinal.modelo.User;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button goToLoginButton;

    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            User newUser = new User(username, password);
            saveUserToFile(newUser);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registrado con éxito");
            alert.setContentText("Tu usuario se ha registrado con exito.");
            alert.showAndWait();

            // Cerrar la ventana de registro al completar el registro
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Registro");
            alert.setHeaderText("Por favor revisa los campos.");
            alert.setContentText("Usuario y contraseña no pueden estar vacios.");
            alert.showAndWait();
        }
    }

    private void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/proyectofinal/persistence/users.txt", true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Método para navegar al formulario de ingreso
    @FXML
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) goToLoginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de ingreso");
            alert.setContentText("Asegúrate de que la ruta al archivo FXML sea correcta.");
            alert.showAndWait();
        }
    }
}
