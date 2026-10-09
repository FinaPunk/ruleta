
package org.example;

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
