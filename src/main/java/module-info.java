module com.example.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Exporta el paquete principal con la clase MainApp
    exports com.example.proyectofinal;

    // Exporta el paquete controlador donde están los controladores
    exports com.example.proyectofinal.controlador;

    // Exporta el paquete modelo para el uso del modelo de datos
    exports com.example.proyectofinal.modelo;

    // Abre el paquete controlador para la reflexión de JavaFX
    opens com.example.proyectofinal.controlador to javafx.fxml;

    // Abre el paquete modelo para la reflexión de JavaFX (si es necesario)
    opens com.example.proyectofinal.modelo to javafx.fxml;

    // Abre el paquete con recursos FXML
    opens com.example.proyectofinal.vista to javafx.fxml;
}
