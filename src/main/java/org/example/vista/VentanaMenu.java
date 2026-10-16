package org.example.vista;

import org.example.controlador.ResultadoController;
import org.example.controlador.RuletaController;
import org.example.controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final SessionController session;
    private JFrame ventana;

    public VentanaMenu(SessionController session) {
        this.session = session;
    }

    public void mostrarVentana() {
        ventana = new JFrame("Casino Black Cat - Menú Principal");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 300);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + session.getUsuarioActual().getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnJugar = new JButton("Ir a la Ruleta");
        JButton btnHistorial = new JButton("Ver Mi Historial");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panel.add(lblBienvenida);
        panel.add(btnJugar);
        panel.add(btnHistorial);
        panel.add(btnCerrarSesion);

        btnJugar.addActionListener(e -> abrirRuleta());
        btnHistorial.addActionListener(e -> abrirHistorial());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        ventana.add(panel);
        ventana.setVisible(true);
    }

    private void abrirRuleta() {
        ventana.dispose();
        RuletaController ruletaController = session.crearControladorRuleta();
        new VentanaRuleta(ruletaController, session).mostrarVentana();
    }

    private void abrirHistorial() {
        ResultadoController resultadoController = new ResultadoController(session);
        new VentanaHistorial(resultadoController, session, ventana).mostrarVentana();
    }

    private void cerrarSesion() {
        session.cerrarSesion();
        ventana.dispose();
        new VentanaLogin(session).mostrarVentana();
    }
}