package org.example.modelo;

import java.time.LocalDateTime;

public class Resultado {

    private final int numeroObtenido;
    private final TipoApuesta tipoApuesta;
    private final int montoApostado;
    private final boolean esAcierto;
    private final LocalDateTime fechaHora;

    public Resultado(int numeroObtenido, TipoApuesta tipoApuesta, int montoApostado, boolean esAcierto) {
        this.numeroObtenido = numeroObtenido;
        this.tipoApuesta = tipoApuesta;
        this.montoApostado = montoApostado;
        this.esAcierto = esAcierto;
        this.fechaHora = LocalDateTime.now();
    }

    public int getNumeroObtenido() {
        return numeroObtenido;
    }

    public TipoApuesta getTipoApuesta() {
        return tipoApuesta;
    }

    public int getMontoApostado() {
        return montoApostado;
    }

    public boolean esAcierto() {
        return esAcierto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
