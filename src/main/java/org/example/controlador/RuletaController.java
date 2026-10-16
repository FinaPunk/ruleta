package org.example.controlador;

import org.example.modelo.Resultado;
import org.example.modelo.Ruleta;
import org.example.modelo.TipoApuesta;

public class RuletaController {

    private final Ruleta ruleta;
    private final SessionController session;

    public RuletaController(Ruleta ruleta, SessionController session) {
        if (ruleta == null || session == null) {
            throw new IllegalArgumentException("La ruleta y la sesión son obligatorias.");
        }
        this.ruleta = ruleta;
        this.session = session;
    }

    public String getNombreUsuario() {
        return session.getUsuarioActual().getNombre();
    }

    public void cambiarNombre(String nombre) {
        session.getUsuarioActual().setNombre(nombre);
    }

    public int getSaldo() {
        return ruleta.getSaldo();
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public boolean realizarApuesta(TipoApuesta tipo, int monto) {
        Resultado resultado = ruleta.apostar(tipo, monto);
        if (resultado != null) {
            session.getUsuarioActual().agregarResultado(resultado);
            return true;
        }
        return false;
    }

    public int getUltimoNumero() {
        return ruleta.getUltimoNumero();
    }

    public boolean getUltimoAcierto() {
        return ruleta.getUltimoAcierto();
    }

    public boolean esRojo(int numero) {
        return ruleta.esRojo(numero);
    }
}