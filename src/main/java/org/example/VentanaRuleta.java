
package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaRuleta {

    private final JFrame frame =
            new JFrame("Casino Black Cat - Ruleta");

    private final ControladorRuleta controlador;

    private final JLabel lblNombre =
            new JLabel("", SwingConstants.CENTER);

    private final JLabel lblSaldo =
            new JLabel("", SwingConstants.CENTER);

    private final JLabel lblResultado =
            new JLabel("¡Haz tu primera apuesta!", SwingConstants.CENTER);

    public VentanaRuleta(ControladorRuleta controlador) {
        this.controlador = controlador;

        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JButton btnRojo = new JButton("Apostar a ROJO");
        JButton btnNegro = new JButton("Apostar a NEGRO");
        JButton btnPar = new JButton("Apostar a PAR");
        JButton btnImpar = new JButton("Apostar a IMPAR");
        JButton btnPerfil = new JButton("Mi perfil");
        JButton btnEstadisticas = new JButton("Ver estadísticas");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        panel.add(lblNombre);
        panel.add(lblSaldo);
        panel.add(btnRojo);
        panel.add(btnNegro);
        panel.add(btnPar);
        panel.add(btnImpar);
        panel.add(btnPerfil);
        panel.add(btnEstadisticas);
        panel.add(btnCerrarSesion);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(lblResultado, BorderLayout.SOUTH);

        btnRojo.addActionListener(
                e -> realizarApuesta(TipoApuesta.ROJO)
        );

        btnNegro.addActionListener(
                e -> realizarApuesta(TipoApuesta.NEGRO)
        );

        btnPar.addActionListener(
                e -> realizarApuesta(TipoApuesta.PAR)
        );

        btnImpar.addActionListener(
                e -> realizarApuesta(TipoApuesta.IMPAR)
        );

        btnPerfil.addActionListener(e -> mostrarPerfil());

        btnEstadisticas.addActionListener(e -> {
            controlador.mostrarEstadisticas();

            JOptionPane.showMessageDialog(
                    frame,
                    "Las estadísticas se muestran en la consola de IntelliJ.",
                    "Estadísticas",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnCerrarSesion.addActionListener(e -> {
            SessionController.getInstancia().cerrarSesion();
            frame.dispose();
            new VentanaLogin().mostrarVentana();
        });

        actualizarDatos();
    }

    private void realizarApuesta(TipoApuesta tipo) {
        String entrada = JOptionPane.showInputDialog(
                frame,
                "Saldo actual: $" + controlador.getSaldo()
                        + "\n¿Cuánto deseas apostar?"
        );

        if (entrada == null) {
            return;
        }

        int monto;

        try {
            monto = Integer.parseInt(entrada.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Ingresa un monto entero válido.",
                    "Monto inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (monto <= 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "La apuesta debe ser mayor que cero.",
                    "Monto inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            boolean realizada = controlador.realizarApuesta(tipo, monto);

            if (!realizada) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Saldo insuficiente para esa apuesta.",
                        "Apuesta rechazada",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int numero = controlador.getUltimoNumero();
            boolean acierto = controlador.getUltimoAcierto();

            String color;

            if (numero == 0) {
                color = "verde";
            } else if (controlador.esRojo(numero)) {
                color = "rojo";
            } else {
                color = "negro";
            }

            String resultado = "Salió el número " + numero
                    + " (" + color + "). ";

            resultado += acierto
                    ? "¡Ganaste!"
                    : "No acertaste esta vez.";

            lblResultado.setText(resultado);
            actualizarDatos();

            JOptionPane.showMessageDialog(
                    frame,
                    resultado + "\nSaldo actual: $"
                            + controlador.getSaldo(),
                    "Resultado de la apuesta",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void mostrarPerfil() {
        String[] opciones = {
                "Ver datos",
                "Cambiar nombre",
                "Depositar dinero",
                "Cancelar"
        };

        int opcion = JOptionPane.showOptionDialog(
                frame,
                "Selecciona una opción:",
                "Mi perfil",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        switch (opcion) {
            case 0:
                JOptionPane.showMessageDialog(
                        frame,
                        "Nombre: " + controlador.getNombreUsuario()
                                + "\nSaldo: $" + controlador.getSaldo(),
                        "Mi perfil",
                        JOptionPane.INFORMATION_MESSAGE
                );
                break;

            case 1:
                cambiarNombre();
                break;

            case 2:
                depositarDinero();
                break;

            default:
                break;
        }

        actualizarDatos();
    }

    private void cambiarNombre() {
        String nombre = JOptionPane.showInputDialog(
                frame,
                "Ingresa tu nuevo nombre:",
                controlador.getNombreUsuario()
        );

        if (nombre == null) {
            return;
        }

        try {
            controlador.cambiarNombre(nombre);

            JOptionPane.showMessageDialog(
                    frame,
                    "Nombre actualizado correctamente."
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "Nombre inválido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void depositarDinero() {
        String entrada = JOptionPane.showInputDialog(
                frame,
                "¿Cuánto dinero deseas depositar?"
        );

        if (entrada == null) {
            return;
        }

        int monto;

        try {
            monto = Integer.parseInt(entrada.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Ingresa un monto entero válido.",
                    "Monto inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            controlador.depositar(monto);
            actualizarDatos();

            JOptionPane.showMessageDialog(
                    frame,
                    "Depósito realizado correctamente.\n"
                            + "Saldo actual: $" + controlador.getSaldo()
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "Depósito rechazado",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void actualizarDatos() {
        lblNombre.setText(
                "Jugador/a: " + controlador.getNombreUsuario()
        );

        lblSaldo.setText(
                "Saldo: $" + controlador.getSaldo()
        );
    }

    public void mostrarVentana() {
        actualizarDatos();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
