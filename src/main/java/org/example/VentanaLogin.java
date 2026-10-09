
package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin {

    private final SessionController session;

    private JFrame ventana;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public VentanaLogin(SessionController session) {
        this.session = session;
    }

    public void mostrarVentana() {
        ventana = new JFrame("Casino Black Cat - Iniciar sesión");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 260);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnIngresar = new JButton("Iniciar sesión");
        JButton btnRegistrar = new JButton("Registrarse");

        panel.add(btnIngresar);
        panel.add(btnRegistrar);

        panel.add(new JLabel("Prueba: admin / 1234"));
        panel.add(new JLabel(""));

        btnIngresar.addActionListener(e -> iniciarSesion());
        btnRegistrar.addActionListener(e -> abrirRegistro());

        ventana.add(panel);
        ventana.setVisible(true);
    }

    private void iniciarSesion() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Ingresa tu usuario y contraseña.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean ingreso = session.iniciarSesion(username, password);

        if (!ingreso) {
            JOptionPane.showMessageDialog(
                    ventana,
                    session.getUltimoError(),
                    "Error de inicio de sesión",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        ControladorRuleta controlador =
                session.crearControladorRuleta();

        ventana.dispose();

        new VentanaRuleta(controlador, session).mostrarVentana();
    }

    private void abrirRegistro() {
        ventana.dispose();
        new VentanaRegistro(session).mostrarVentana();
    }
}
