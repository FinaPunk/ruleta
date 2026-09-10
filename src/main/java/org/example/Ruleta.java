package org.example;

import java.util.Random;
import java.util.Scanner;

public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static final int CANTIDAD_NUMEROS = 37;

    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];

    public static int historialSize = 0;

    public static Random rng = new Random();

    public static int[] numerosRojos = {
            1, 3, 5, 7, 9,
            12, 14, 16, 18,
            19, 21, 23, 25, 27,
            30, 32, 34, 36
    };

    public static void main(String[] args) {

        menu();
    }

    public static void menu() {

        Scanner in = new Scanner(System.in);

        int opcion;

        do {

            mostrarMenu();

            opcion = leerOpcion(in);

            ejecutarOpcion(opcion, in);

        } while (opcion != 3);
    }

    public static void mostrarMenu() {

        System.out.println();
        System.out.println("===== CASINO BLACK CAT =====");
        System.out.println("1. Iniciar ronda");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static int leerOpcion(Scanner in) {

        if (in.hasNextInt()) {

            return in.nextInt();

        } else {

            in.next();

            return 0;
        }
    }

    public static void ejecutarOpcion(int opcion, Scanner in) {

        switch (opcion) {

            case 1:
                iniciarRonda(in);
                break;

            case 2:
                mostrarEstadisticas();
                break;

            case 3:
                System.out.println("Gracias por jugar.");
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }

    public static void iniciarRonda(Scanner in) {

        char tipo = leerTipoApuesta(in);

        System.out.print("Ingrese el monto a apostar: ");

        while (!in.hasNextInt()) {

            System.out.println("Debe ingresar un número.");
            in.next();
            System.out.print("Ingrese el monto a apostar: ");
        }

        int monto = in.nextInt();

        while (monto <= 0) {

            System.out.println("El monto debe ser mayor que 0.");
            System.out.print("Ingrese el monto a apostar: ");

            while (!in.hasNextInt()) {

                System.out.println("Debe ingresar un número.");
                in.next();
                System.out.print("Ingrese el monto a apostar: ");
            }

            monto = in.nextInt();
        }

        int numero = girarRuleta();

        boolean acierto = evaluarResultado(numero, tipo);

        registrarResultado(numero, monto, acierto);

        mostrarResultado(numero, tipo, monto, acierto);
    }

    public static char leerTipoApuesta(Scanner in) {

        char tipo;

        do {

            System.out.println();
            System.out.println("Seleccione su apuesta:");
            System.out.println("R - Rojo");
            System.out.println("N - Negro");
            System.out.println("P - Par");
            System.out.println("I - Impar");
            System.out.print("Opción: ");

            String entrada = in.next();

            if (entrada.length() == 1) {

                tipo = Character.toUpperCase(entrada.charAt(0));

            } else {

                tipo = ' ';
            }

            if (tipo != 'R' &&
                    tipo != 'N' &&
                    tipo != 'P' &&
                    tipo != 'I') {

                System.out.println("Tipo de apuesta inválido.");
            }

        } while (
                tipo != 'R' &&
                        tipo != 'N' &&
                        tipo != 'P' &&
                        tipo != 'I'
        );

        return tipo;
    }

    public static int girarRuleta() {

        return rng.nextInt(CANTIDAD_NUMEROS);
    }

    public static boolean evaluarResultado(int numero, char tipo) {

        tipo = Character.toUpperCase(tipo);

        if (numero == 0) {

            return false;
        }

        switch (tipo) {

            case 'R':
                return esRojo(numero);

            case 'N':
                return !esRojo(numero);

            case 'P':
                return numero % 2 == 0;

            case 'I':
                return numero % 2 != 0;

            default:
                return false;
        }
    }

    public static boolean esRojo(int n) {

        for (int numeroRojo : numerosRojos) {

            if (numeroRojo == n) {

                return true;
            }
        }

        return false;
    }

    public static void registrarResultado(
            int numero,
            int apuesta,
            boolean acierto) {

        if (historialSize < MAX_HISTORIAL) {

            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = apuesta;
            historialAciertos[historialSize] = acierto;

            historialSize++;
        }
    }

    public static void mostrarResultado(
            int numero,
            char tipo,
            int monto,
            boolean acierto) {

        System.out.println();
        System.out.println("===== RESULTADO =====");
        System.out.println("Número: " + numero);
        System.out.println("Apuesta: " + tipo);
        System.out.println("Monto: $" + monto);

        if (acierto) {

            System.out.println("¡GANASTE!");

        } else {

            System.out.println("PERDISTE.");
        }
    }

    public static void mostrarEstadisticas() {

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

        int gananciaNeta =
                totalAciertos * 2 * (totalApostado / Math.max(historialSize, 1))
                        - totalApostado;

        System.out.println();
        System.out.println("===== ESTADÍSTICAS =====");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Monto total apostado: $" + totalApostado);
        System.out.println("Total de aciertos: " + totalAciertos);
        System.out.println("Porcentaje de aciertos: " + porcentaje + "%");
        System.out.println("Ganancia/pérdida neta: $" + gananciaNeta);
    }
}