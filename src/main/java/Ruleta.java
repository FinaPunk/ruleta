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
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
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

        in.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n===== RULETA =====");
        System.out.println("1. Iniciar ronda");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
    }

    public static int leerOpcion(Scanner in) {
        while (true) {
            System.out.print("Opción: ");
            String entrada = in.next().trim();

            if (entrada.matches("[1-3]")) {
                return Integer.parseInt(entrada);
            }

            System.out.println("Opción inválida.");
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
                System.out.println("¡Gracias por jugar!");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    public static void iniciarRonda(Scanner in) {
        char tipo = leerTipoApuesta(in);
        int monto = leerMonto(in);

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipo);

        registrarResultado(numero, monto, acierto);
        mostrarResultado(numero, tipo, monto, acierto);
    }

    public static char leerTipoApuesta(Scanner in) {
        while (true) {
            System.out.print("Tipo de apuesta (R=Rojo, N=Negro, P=Par, I=Impar): ");

            String entrada = in.next().trim().toUpperCase();

            if (entrada.matches("[RNPI]")) {
                return entrada.charAt(0);
            }

            System.out.println("Tipo de apuesta inválido.");
        }
    }

    public static int leerMonto(Scanner in) {
        while (true) {
            System.out.print("Monto a apostar: ");
            String entrada = in.next().trim();

            // Solo acepta enteros positivos
            if (entrada.matches("[0-9]+")) {
                int monto = Integer.parseInt(entrada);

                if (monto > 0) {
                    return monto;
                }
            }

            System.out.println("Monto inválido. Ingrese un número entero mayor que 0.");
        }
    }

    public static int girarRuleta() {
        return rng.nextInt(CANTIDAD_NUMEROS);
    }

    public static boolean evaluarResultado(int numero, char tipo) {

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
        for (int rojo : numerosRojos) {
            if (rojo == n) {
                return true;
            }
        }
        return false;
    }

    public static void registrarResultado(int numero, int apuesta, boolean acierto) {

        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = apuesta;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }

    public static void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {

        System.out.println("\n===== RESULTADO =====");
        System.out.println("Número obtenido: " + numero);
        System.out.println("Apuesta: " + tipo);
        System.out.println("Monto: $" + monto);

        if (acierto) {
            System.out.println("¡GANASTE!");
        } else {
            System.out.println("Perdiste.");
        }
    }

    public static void mostrarEstadisticas() {

        int totalApostado = 0;
        int aciertos = 0;
        int gananciaNeta = 0;

        for (int i = 0; i < historialSize; i++) {

            totalApostado += historialApuestas[i];

            if (historialAciertos[i]) {
                aciertos++;
                gananciaNeta += historialApuestas[i];
            } else {
                gananciaNeta -= historialApuestas[i];
            }
        }

        double porcentaje = 0;

        if (historialSize > 0) {
            porcentaje = (aciertos * 100.0) / historialSize;
        }

        System.out.println("\n===== ESTADÍSTICAS =====");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Monto total apostado: $" + totalApostado);
        System.out.println("Cantidad de aciertos: " + aciertos);
        System.out.printf("Porcentaje de aciertos: %.2f%%\n", porcentaje);
        System.out.println("Ganancia/Pérdida neta: $" + gananciaNeta);
    }
}