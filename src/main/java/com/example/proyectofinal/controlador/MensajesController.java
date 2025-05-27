package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MensajesController {

    @FXML
    private ListView<String> listConversaciones;

    @FXML
    private TextArea areaMensajes;

    @FXML
    private TextField txtMensaje;

    public void initialize() {
        listConversaciones.getItems().addAll("Carlos", "Ana", "Luis");
    }

    @FXML
    public void enviarMensaje() {
        String texto = txtMensaje.getText();
        if (!texto.isEmpty()) {
            areaMensajes.appendText("Tú: " + texto + "\n");
            txtMensaje.clear();
        }
    }
}
