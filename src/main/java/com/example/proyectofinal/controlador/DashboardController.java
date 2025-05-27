package com.example.proyectofinal.controlador;

import com.example.proyectofinal.estructuras.ArbolBinarioBusqueda;
import com.example.proyectofinal.estructuras.GrafoAfinidad;
import com.example.proyectofinal.estructuras.ColaPrioridadAyuda;
import com.example.proyectofinal.modelo.Contenido;
import com.example.proyectofinal.modelo.SolicitudAyuda;
import com.example.proyectofinal.modelo.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.ListView;

public class DashboardController {

    private ArbolBinarioBusqueda arbolContenidos;
    private GrafoAfinidad grafo;
    private ColaPrioridadAyuda colaAyuda;

    @FXML
    private VBox contenedorContenido;

    @FXML
    private TextField txtTitulo, txtAutor, txtTema, txtTipo;

    @FXML
    private ComboBox<String> comboUrgencia;

    @FXML
    private TextArea txtAreaAyuda;

    @FXML
    private ListView<String> listContenidos, listSolicitudes;

    private User usuarioActual;

    public void initialize() {
        arbolContenidos = new ArbolBinarioBusqueda();
        grafo = new GrafoAfinidad();
        colaAyuda = new ColaPrioridadAyuda();

        comboUrgencia.getItems().addAll("1 - Baja", "2 - Media", "3 - Alta");
        comboUrgencia.getSelectionModel().select(1);

        // Usuario de prueba
        usuarioActual = new User("juan", "123", "estudiante");
        grafo.agregarUsuario(usuarioActual);
    }

    @FXML
    private void publicarContenido() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String tema = txtTema.getText();
        String tipo = txtTipo.getText();

        Contenido nuevo = new Contenido(titulo, autor, tema, tipo);
        arbolContenidos.insertar(nuevo);
        usuarioActual.publicarContenido(nuevo);

        listContenidos.getItems().add(nuevo.toString());
        limpiarCampos();
    }

    @FXML
    private void solicitarAyuda() {
        String tema = txtAreaAyuda.getText();
        int urgencia = comboUrgencia.getSelectionModel().getSelectedIndex() + 1;

        SolicitudAyuda solicitud = new SolicitudAyuda(usuarioActual, tema, urgencia);
        colaAyuda.encolar(solicitud);

        actualizarListaSolicitudes();
        txtAreaAyuda.clear();
    }

    private void actualizarListaSolicitudes() {
        listSolicitudes.getItems().clear();
        colaAyuda.imprimirColaEn(listSolicitudes);
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtTema.clear();
        txtTipo.clear();
    }

}