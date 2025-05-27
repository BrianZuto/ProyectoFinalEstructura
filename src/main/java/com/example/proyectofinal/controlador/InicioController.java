package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InicioController {

    @FXML
    private TextField txtBuscar;

    @FXML
    private ListView<String> listResultados;

    @FXML
    private TextArea areaPerfil;

    @FXML
    public void buscarContenido() {
        String termino = txtBuscar.getText();
        listResultados.getItems().clear();
        listResultados.getItems().add("🔍 Resultados para: " + termino);
        // Aquí se puede integrar la búsqueda real en el ABB
    }

    public void mostrarPerfil(String perfil) {
        areaPerfil.setText(perfil);
    }
}
