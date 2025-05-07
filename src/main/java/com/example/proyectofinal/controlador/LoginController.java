package com.example.proyectofinal.controlador;

import com.example.proyectofinal.modelo.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button goToRegisterButton;

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateUser(username, password)) {
            // Si las credenciales son correctas, abrir el dashboard
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/dashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Dashboard");
                stage.show();

                // Cerrar la ventana de login
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo cargar la vista del panel");
                alert.setContentText("Por favor, compruebe las rutas de los archivos.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Ingreso");
            alert.setHeaderText("Credenciales Inválidas");
            alert.setContentText("Por favor comprueba tu usuario y contraseña.");
            alert.showAndWait();
        }
    }

    private boolean validateUser(String username, String password) {
        // Leer el archivo users.txt
        File file = new File("src/main/resources/com/example/proyectofinal/persistence/users.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 2 && userData[0].equals(username) && userData[1].equals(password)) {
                    return true; // Usuario válido
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Si no se encuentra el usuario o las credenciales no coinciden
    }


    // Método para navegar al formulario de registro
    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/register.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Registro");
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) goToRegisterButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de registro");
            alert.setContentText("Asegúrate de que la ruta al archivo FXML sea correcta.");
            alert.showAndWait();
        }
    }
}
