package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaRuleta {

    private final JFrame frame = new JFrame("Casino Black Cat - Ruleta");

    private final JLabel lblBienvenida;
    private final JLabel lblResultado = new JLabel("Resultado: -");

    private final JButton btnRojo = new JButton("Rojo");
    private final JButton btnNegro = new JButton("Negro");
    private final JButton btnPar = new JButton("Par");
    private final JButton btnImpar = new JButton("Impar");
    private final JButton btnSalir = new JButton("Cerrar sesión");

    public VentanaRuleta(String nombre) {

        lblBienvenida = new JLabel("Bienvenido/a, " + nombre);

        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 1, 10, 10));

        frame.add(lblBienvenida);
        frame.add(lblResultado);
        frame.add(btnRojo);
        frame.add(btnNegro);
        frame.add(btnPar);
        frame.add(btnImpar);
        frame.add(btnSalir);

        btnRojo.addActionListener(e -> jugar('R'));
        btnNegro.addActionListener(e -> jugar('N'));
        btnPar.addActionListener(e -> jugar('P'));
        btnImpar.addActionListener(e -> jugar('I'));

        btnSalir.addActionListener(e -> cerrarSesion());
    }

    public void mostrarVentana() {

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void jugar(char tipo) {

        int numero = Ruleta.girarRuleta();

        boolean acierto = Ruleta.evaluarResultado(numero, tipo);

        String color;

        if (numero == 0) {
            color = "Verde";
        } else if (Ruleta.esRojo(numero)) {
            color = "Rojo";
        } else {
            color = "Negro";
        }

        if (acierto) {

            lblResultado.setText(
                    "Resultado: " + numero +
                            " - " + color +
                            " | ¡Ganaste!"
            );

        } else {

            lblResultado.setText(
                    "Resultado: " + numero +
                            " - " + color +
                            " | Perdiste."
            );
        }
    }

    private void cerrarSesion() {

        frame.dispose();

        new VentanaLogin().mostrarVentana();
    }
}