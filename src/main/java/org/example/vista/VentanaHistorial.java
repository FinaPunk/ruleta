package org.example.vista;

import org.example.controlador.ResultadoController;
import org.example.controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaHistorial {

    private final ResultadoController resultadoController;
    private final SessionController session;
    private final JFrame ventanaPadre;

    public VentanaHistorial(ResultadoController resultadoController, SessionController session, JFrame ventanaPadre) {
        this.resultadoController = resultadoController;
        this.session = session;
        this.ventanaPadre = ventanaPadre;
    }

    public void mostrarVentana() {
        JDialog dialog = new JDialog(ventanaPadre, "Historial - " + session.getUsuarioActual().getNombre(), true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(ventanaPadre);

        JTextArea txtArea = new JTextArea(resultadoController.obtenerHistorialFormateado());
        txtArea.setEditable(false);
        txtArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(txtArea);
        dialog.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}