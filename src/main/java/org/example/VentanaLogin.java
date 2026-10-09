
package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin {

    private final JFrame frame =
            new JFrame("Casino Black Cat - Iniciar sesión");

    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();

    private final SessionController sessionController =
            SessionController.getInstancia();

    public VentanaLogin() {
        frame.setSize(400, 230);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JButton btnIngresar = new JButton("Ingresar");
        JButton btnRegistrar = new JButton("Registrarse");

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtClave);
        panel.add(btnIngresar);
        panel.add(btnRegistrar);

        frame.add(panel);

        btnIngresar.addActionListener(e -> login());

        btnRegistrar.addActionListener(e -> {
            frame.dispose();
            new VentanaRegistro().mostrarVentana();
        });
    }

    private void login() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtClave.getPassword());

        Usuario usuario = sessionController.iniciarSesion(
                username,
                password
        );

        if (usuario == null) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Usuario o contraseña incorrectos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        ControladorRuleta controlador = new ControladorRuleta(
                usuario,
                sessionController.getRuletaActual()
        );

        frame.dispose();
        new VentanaRuleta(controlador).mostrarVentana();
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
