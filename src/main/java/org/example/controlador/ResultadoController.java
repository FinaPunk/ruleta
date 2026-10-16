package org.example.controlador;

import org.example.modelo.Resultado;
import org.example.modelo.Usuario;

import java.util.List;

public class ResultadoController {

    private final SessionController session;

    public ResultadoController(SessionController session) {
        this.session = session;
    }

    public List<Resultado> obtenerHistorial() {
        Usuario usuario = session.getUsuarioActual();
        if (usuario == null) {
            throw new IllegalStateException("No existe un usuario activo en sesión.");
        }
        return usuario.getHistorialResultados();
    }

    public String obtenerHistorialFormateado() {
        List<Resultado> historial = obtenerHistorial();
        if (historial.isEmpty()) {
            return "Aún no has realizado ninguna apuesta.";
        }

        StringBuilder sb = new StringBuilder("===== HISTORIAL DE JUGADAS =====\n\n");
        int ganadas = 0;

        for (int i = 0; i < historial.size(); i++) {
            Resultado r = historial.get(i);
            sb.append("Apuesta #").append(i + 1)
                    .append(" | Número: ").append(r.getNumeroObtenido())
                    .append(" | Tipo: ").append(r.getTipoApuesta())
                    .append(" | Monto: $").append(r.getMontoApostado())
                    .append(" | Estado: ").append(r.esAcierto() ? "GANADA" : "PERDIDA")
                    .append("\n");

            if (r.esAcierto()) {
                ganadas++;
            }
        }

        sb.append("\nTotal jugadas: ").append(historial.size())
                .append("\nGanadas: ").append(ganadas)
                .append("\nPerdidas: ").append(historial.size() - ganadas);

        return sb.toString();
    }
}