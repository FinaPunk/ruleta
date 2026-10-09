
package org.example;

import java.util.Random;

public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static final int CANTIDAD_NUMEROS = 37;

    private int saldo;
    private final Random random = new Random();

    private final int[] historialNumeros =
            new int[MAX_HISTORIAL];

    private final TipoApuesta[] historialApuestas =
            new TipoApuesta[MAX_HISTORIAL];

    private final boolean[] historialAciertos =
            new boolean[MAX_HISTORIAL];

    private int historialSize;
    private int ultimoNumero = -1;
    private boolean ultimoAcierto;

    public Ruleta() {
        this(0);
    }

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException(
                    "El saldo inicial no puede ser negativo."
            );
        }

        this.saldo = saldoInicial;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getHistorialSize() {
        return historialSize;
    }

    public int getUltimoNumero() {
        return ultimoNumero;
    }

    public boolean getUltimoAcierto() {
        return ultimoAcierto;
    }

    public int girarRuleta() {
        return random.nextInt(CANTIDAD_NUMEROS);
    }

    public boolean esRojo(int numero) {
        int[] numerosRojos = {
                1, 3, 5, 7, 9, 12, 14, 16, 18,
                19, 21, 23, 25, 27, 30, 32, 34, 36
        };

        for (int rojo : numerosRojos) {
            if (numero == rojo) {
                return true;
            }
        }

        return false;
    }

    public boolean evaluarResultado(
            int numero,
            TipoApuesta tipo
    ) {
        if (numero < 0 || numero >= CANTIDAD_NUMEROS) {
            throw new IllegalArgumentException(
                    "El número debe estar entre 0 y 36."
            );
        }

        if (tipo == null) {
            throw new IllegalArgumentException(
                    "El tipo de apuesta es obligatorio."
            );
        }

        switch (tipo) {
            case ROJO:
                return numero != 0 && esRojo(numero);

            case NEGRO:
                return numero != 0 && !esRojo(numero);

            case PAR:
                return numero != 0 && numero % 2 == 0;

            case IMPAR:
                return numero % 2 != 0;

            default:
                return false;
        }
    }

    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El depósito debe ser mayor que cero."
            );
        }

        if (saldo > Integer.MAX_VALUE - monto) {
            throw new IllegalArgumentException(
                    "El depósito supera el saldo máximo permitido."
            );
        }

        saldo += monto;
    }

    public boolean apostar(TipoApuesta tipo, int monto) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un tipo de apuesta."
            );
        }

        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "La apuesta debe ser mayor que cero."
            );
        }

        if (monto > Integer.MAX_VALUE / 2) {
            throw new IllegalArgumentException(
                    "El monto de la apuesta es demasiado grande."
            );
        }

        if (monto > saldo) {
            return false;
        }

        saldo -= monto;

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipo);

        if (acierto) {
            saldo += monto * 2;
        }

        ultimoNumero = numero;
        ultimoAcierto = acierto;

        guardarHistorial(numero, tipo, acierto);

        return true;
    }

    private void guardarHistorial(
            int numero,
            TipoApuesta tipo,
            boolean acierto
    ) {
        if (historialSize == MAX_HISTORIAL) {
            System.arraycopy(
                    historialNumeros, 1,
                    historialNumeros, 0,
                    MAX_HISTORIAL - 1
            );

            System.arraycopy(
                    historialApuestas, 1,
                    historialApuestas, 0,
                    MAX_HISTORIAL - 1
            );

            System.arraycopy(
                    historialAciertos, 1,
                    historialAciertos, 0,
                    MAX_HISTORIAL - 1
            );

            historialSize--;
        }

        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = tipo;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    public void mostrarEstadisticas() {
        if (historialSize == 0) {
            System.out.println("Todavía no hay apuestas registradas.");
            return;
        }

        int aciertos = 0;

        System.out.println("===== HISTORIAL DE RULETA =====");

        for (int i = 0; i < historialSize; i++) {
            System.out.println(
                    "Apuesta " + (i + 1)
                            + " | Número: " + historialNumeros[i]
                            + " | Tipo: " + historialApuestas[i]
                            + " | Resultado: "
                            + (historialAciertos[i] ? "Ganada" : "Perdida")
            );

            if (historialAciertos[i]) {
                aciertos++;
            }
        }

        System.out.println("Total de apuestas: " + historialSize);
        System.out.println("Apuestas ganadas: " + aciertos);
        System.out.println(
                "Apuestas perdidas: " + (historialSize - aciertos)
        );
        System.out.println("Saldo actual: $" + saldo);
    }
}
