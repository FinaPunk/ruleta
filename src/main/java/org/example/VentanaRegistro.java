
package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

    private final JFrame frame =
            new JFrame("Casino Black Cat - Registro");

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();

    private final SessionController sessionController =
            SessionController.getInstancia();

    public VentanaRegistro() {
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JButton btnRegistrar = new JButton("Crear cuenta");
        JButton btnVolver = new JButton("Volver al inicio");

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtClave);
        panel.add(btnRegistrar);
        panel.add(btnVolver);

        frame.add(panel);

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnVolver.addActionListener(e -> volverAlLogin());
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String username = txtUsuario.getText().trim();
        String password = new String(txtClave.getPassword());

        if (nombre.isEmpty() || username.isEmpty()
                || password.isBlank()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Completa todos los campos.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            boolean registrado = sessionController.registrarUsuario(
                    username,
                    password,
                    nombre
            );

            if (!registrado) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Ese nombre de usuario ya está registrado.",
                        "Usuario existente",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Cuenta creada correctamente. Ahora inicia sesión."
            );

            volverAlLogin();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void volverAlLogin() {
        frame.dispose();
        new VentanaLogin().mostrarVentana();
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
