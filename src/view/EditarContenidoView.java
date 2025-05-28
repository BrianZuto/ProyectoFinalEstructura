package view;

import model.Contenido;

import javax.swing.*;
import java.awt.*;

public class EditarContenidoView extends JFrame {

    public EditarContenidoView(Contenido contenido) {
        setTitle("Editar Contenido");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField campoTitulo = new JTextField(contenido.getTitulo());
        JTextField campoTipo = new JTextField(contenido.getTipo());
        JTextField campoTema = new JTextField(contenido.getTema());
        JTextArea campoDescripcion = new JTextArea(contenido.getDescripcion());

        JButton guardarBtn = new JButton("Guardar Cambios");

        add(new JLabel("Título:")); add(campoTitulo);
        add(new JLabel("Tipo:")); add(campoTipo);
        add(new JLabel("Tema:")); add(campoTema);
        add(new JLabel("Descripción:")); add(new JScrollPane(campoDescripcion));
        add(new JLabel()); add(guardarBtn);

        guardarBtn.addActionListener(e -> {
            contenido.setTitulo(campoTitulo.getText().trim());
            contenido.setTipo(campoTipo.getText().trim());
            contenido.setTema(campoTema.getText().trim());
            contenido.setDescripcion(campoDescripcion.getText().trim());

            JOptionPane.showMessageDialog(this, "Contenido actualizado con éxito.");
            dispose();
        });

        setVisible(true);
    }
}
