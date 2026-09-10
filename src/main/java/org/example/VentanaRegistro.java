package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

    private final JFrame frame = new JFrame("Registro - Casino Black Cat");

    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();

    private final JLabel lblNombre = new JLabel("Nombre:");
    private final JTextField txtNombre = new JTextField();

    private final JButton btnRegistrar = new JButton("Registrar");
    private final JButton btnVolver = new JButton("Volver");

    public VentanaRegistro() {

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        frame.add(lblUsuario);
        frame.add(txtUsuario);

        frame.add(lblClave);
        frame.add(txtClave);

        frame.add(lblNombre);
        frame.add(txtNombre);

        frame.add(btnRegistrar);
        frame.add(btnVolver);

        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> volverLogin());
    }

    public void mostrarVentana() {

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void registrar() {

        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());
        String nombre = txtNombre.getText().trim();

        if (usuario.isEmpty() || clave.isEmpty() || nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Todos los campos deben estar completos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        for (Usuario u : VentanaLogin.USUARIOS) {

            if (u.validarCredenciales(usuario, clave)) {

                JOptionPane.showMessageDialog(
                        frame,
                        "El usuario ya existe.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        VentanaLogin.USUARIOS.add(
                new Usuario(usuario, clave, nombre)
        );

        JOptionPane.showMessageDialog(
                frame,
                "Usuario registrado correctamente."
        );

        volverLogin();
    }

    private void volverLogin() {

        frame.dispose();

        new VentanaLogin().mostrarVentana();
    }
}