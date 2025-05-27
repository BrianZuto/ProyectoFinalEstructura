import controller.AuthController;
import controller.AyudaController;
import controller.ContenidoController;
import view.LoginView;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        AuthController authController = new AuthController();
        AyudaController ayudaController = new AyudaController();
        ContenidoController contenidoController = new ContenidoController();

        LinkedList<String> intereses1 = new LinkedList<>(Arrays.asList("matemáticas", "programación"));
        LinkedList<String> intereses2 = new LinkedList<>(Arrays.asList("historia", "literatura"));
        LinkedList<String> intereses3 = new LinkedList<>(Arrays.asList("física", "IA"));

        authController.registrarEstudiante("Carlos Ruiz", "carlos@correo.com", "1234", intereses1);
        authController.registrarEstudiante("Ana López", "ana@correo.com", "abcd", intereses2);
        authController.registrarEstudiante("Kevin Torres", "kevin@correo.com", "kevin123", intereses3);
        authController.registrarModerador("Prof. Martínez", "admin", "admin");

        new LoginView(authController, ayudaController, contenidoController);
    }
}
