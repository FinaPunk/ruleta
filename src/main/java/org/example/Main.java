package org.example;

import org.example.controlador.SessionController;
import org.example.vista.VentanaLogin;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SessionController session = new SessionController();
            VentanaLogin ventanaLogin = new VentanaLogin(session);
            ventanaLogin.mostrarVentana();
        });
    }
}
