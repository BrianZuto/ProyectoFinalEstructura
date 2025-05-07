package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button logoutButton;

    @FXML
    public void handleLogout() {
        try {
            // Redirigir al login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinal/vista/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ingreso");
            stage.show();

            // Cerrar la ventana actual (dashboard)
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de inicio de sesión");
            alert.setContentText("Por favor, compruebe las rutas de los archivos.");
            alert.showAndWait();
        }
    }
}
