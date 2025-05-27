package com.example.proyectofinal.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GruposController {

    @FXML
    private ListView<String> listGrupos;

    @FXML
    private Label lblEstado;

    public void initialize() {
        listGrupos.getItems().addAll(
                "Grupo 1 - Programación",
                "Grupo 2 - Estructuras de Datos",
                "Grupo 3 - Matemáticas"
        );
    }

    @FXML
    public void unirseAGrupo() {
        String seleccionado = listGrupos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lblEstado.setText("✅ Te uniste a: " + seleccionado);
        } else {
            lblEstado.setText("❗Selecciona un grupo primero.");
        }
    }
}