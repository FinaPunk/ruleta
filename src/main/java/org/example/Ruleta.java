
package org.example;

import java.util.Random;

public class Ruleta {

    private static final int MAX_HISTORIAL = 100;
    private static final int CANTIDAD_NUMEROS = 37;

    private int saldo;
    private final Random rng;

    private final int[] historialNumeros;
    private final int[] historialApuestas;
    private final boolean[] historialAciertos;
    private int historialSize;

    private int ultimoNumero;
    private boolean ultimoAcierto;

    private static final int[] NUMEROS_ROJOS = {
            1, 3, 5, 7, 9,
            12, 14, 16, 18,
            19, 21, 23, 25, 27,
            30, 32, 34, 36
    };

    // Constructor sin parámetros: saldo inicial cero
    public Ruleta() {
        this(0);
    }

    // Constructor con saldo inicial
    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException(
                    "El saldo inicial no puede ser negativo."
            );
        }

        this.saldo = saldoInicial;
        this.rng = new Random();

        this.historialNumeros = new int[MAX_HISTORIAL];
        this.historialApuestas = new int[MAX_HISTORIAL];
        this.historialAciertos = new boolean[MAX_HISTORIAL];
        this.historialSize = 0;

        this.ultimoNumero = -1;
        this.ultimoAcierto = false;
    }

    // Consultar saldo
    public int getSaldo() {
        return saldo;
    }

    // Recargar saldo
    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El depósito debe ser mayor que cero."
            );
        }

        if (saldo > Integer.MAX_VALUE - monto) {
            throw new IllegalArgumentException(
                    "El monto supera el saldo máximo permitido."
            );
        }

        saldo += monto;
    }

    // Girar la ruleta
    public int girarRuleta() {
        return rng.nextInt(CANTIDAD_NUMEROS);
    }

    // Evaluar si una apuesta es ganadora
    public boolean evaluarResultado(
            int numero,
            TipoApuesta tipo
    ) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "El tipo de apuesta no puede ser nulo."
            );
        }

        if (numero < 0 || numero >= CANTIDAD_NUMEROS) {
            throw new IllegalArgumentException(
                    "El número debe estar entre 0 y 36."
            );
        }

        // El cero no gana en estas cuatro apuestas
        if (numero == 0) {
            return false;
        }

        return switch (tipo) {
            case ROJO -> esRojo(numero);
            case NEGRO -> !esRojo(numero);
            case PAR -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }

    // Comprobar si un número es rojo
    public boolean esRojo(int numero) {
        for (int rojo : NUMEROS_ROJOS) {
            if (rojo == numero) {
                return true;
            }
        }

        return false;
    }

    // Procesar una apuesta y actualizar el saldo
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

        if (monto > saldo) {
            throw new IllegalArgumentException(
                    "No tienes saldo suficiente."
            );
        }

        if (historialSize >= MAX_HISTORIAL) {
            throw new IllegalStateException(
                    "El historial está lleno."
            );
        }

        // Descontar el monto apostado
        saldo -= monto;

        ultimoNumero = girarRuleta();
        ultimoAcierto = evaluarResultado(ultimoNumero, tipo);

        // Si gana, recibe el doble de lo apostado,
        // incluyendo la devolución de su apuesta.
        if (ultimoAcierto) {
            saldo += monto * 2;
        }

        registrarResultado(
                ultimoNumero,
                monto,
                ultimoAcierto
        );

        return ultimoAcierto;
    }

    // Guardar una ronda
    private void registrarResultado(
            int numero,
            int monto,
            boolean acierto
    ) {
        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = monto;
        historialAciertos[historialSize] = acierto;

        historialSize++;
    }

    // Consultar el último número obtenido
    public int getUltimoNumero() {
        return ultimoNumero;
    }

    // Consultar el resultado de la última apuesta
    public boolean getUltimoAcierto() {
        return ultimoAcierto;
    }

    // Consultar cuántas rondas se han jugado
    public int getHistorialSize() {
        return historialSize;
    }

    // Calcular y mostrar estadísticas
    public void mostrarEstadisticas() {
        int totalApostado = 0;
        int totalAciertos = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i];

            if (historialAciertos[i]) {
                totalAciertos++;
            }
        }

        double porcentaje = 0;

        if (historialSize > 0) {
            porcentaje =
                    (double) totalAciertos / historialSize * 100;
        }

        System.out.println();
        System.out.println("===== ESTADÍSTICAS =====");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Monto total apostado: $" + totalApostado);
        System.out.println("Total de aciertos: " + totalAciertos);
        System.out.printf(
                "Porcentaje de aciertos: %.2f%%%n",
                porcentaje
        );
        System.out.println("Saldo actual: $" + saldo);
    }
}
