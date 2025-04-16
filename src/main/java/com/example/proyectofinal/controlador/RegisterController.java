package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            User newUser = new User(username, password);
            saveUserToFile(newUser);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.setHeaderText("Registration Successful");
            alert.setContentText("Your account has been created.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Register Error");
            alert.setHeaderText("Please fill in both fields.");
            alert.setContentText("Username and Password cannot be empty.");
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

}
