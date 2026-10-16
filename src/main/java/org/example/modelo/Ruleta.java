package org.example.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ruleta {

    private static final int CANTIDAD_NUMEROS = 37;

    private int saldo;
    private final Random random = new Random();
    private final List<Resultado> resultadosGenerados = new ArrayList<>();

    private int ultimoNumero = -1;
    private boolean ultimoAcierto;

    public Ruleta() {
        this(0);
    }

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.saldo = saldoInicial;
    }

    public int getSaldo() {
        return saldo;
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

    public boolean evaluarResultado(int numero, TipoApuesta tipo) {
        if (numero < 0 || numero >= CANTIDAD_NUMEROS) {
            throw new IllegalArgumentException("El número debe estar entre 0 y 36.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de apuesta es obligatorio.");
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
            throw new IllegalArgumentException("El depósito debe ser mayor que cero.");
        }
        if ((long) saldo + monto > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("El depósito supera el saldo máximo permitido.");
        }
        saldo += monto;
    }

    public Resultado apostar(TipoApuesta tipo, int monto) {
        if (tipo == null) {
            throw new IllegalArgumentException("Debes seleccionar un tipo de apuesta.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("La apuesta debe ser mayor que cero.");
        }
        if (monto > Integer.MAX_VALUE / 2) {
            throw new IllegalArgumentException("El monto de la apuesta es demasiado grande.");
        }
        if (monto > saldo) {
            return null;
        }

        saldo -= monto;

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipo);

        if (acierto) {
            saldo += monto * 2;
        }

        ultimoNumero = numero;
        ultimoAcierto = acierto;

        Resultado resultado = new Resultado(numero, tipo, monto, acierto);
        resultadosGenerados.add(resultado);

        return resultado;
    }

    public List<Resultado> getResultadosGenerados() {
        return Collections.unmodifiableList(resultadosGenerados);
    }
}