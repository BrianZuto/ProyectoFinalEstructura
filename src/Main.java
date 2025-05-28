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

        authController.registrarEstudiante("Luis Torres", "luis@gmail.com", "12345", intereses1);
        authController.registrarEstudiante("Luis Osorio", "luismanuel@correo.com", "abcd", intereses2);
        authController.registrarEstudiante("Carolina", "carolina@correo.com", "caro123", intereses3);
        authController.registrarModerador("Profesor Manuel", "admin", "admin");

        new LoginView(authController, ayudaController, contenidoController);
    }
}
