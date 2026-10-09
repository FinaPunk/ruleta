
package org.example;

public class ControladorRuleta {

    private final Usuario usuario;
    private final Ruleta ruleta;

    public ControladorRuleta(Usuario usuario, Ruleta ruleta) {
        if (usuario == null || ruleta == null) {
            throw new IllegalArgumentException(
                    "El usuario y la ruleta son obligatorios."
            );
        }

        this.usuario = usuario;
        this.ruleta = ruleta;
    }

    public String getNombreUsuario() {
        return usuario.getNombre();
    }

    public void cambiarNombre(String nombre) {
        usuario.setNombre(nombre);
    }

    public int getSaldo() {
        return ruleta.getSaldo();
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public boolean realizarApuesta(TipoApuesta tipo, int monto) {
        return ruleta.apostar(tipo, monto);
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

    public String getEstadisticas() {
        return ruleta.getEstadisticas();
    }

    public void mostrarEstadisticas() {
        ruleta.mostrarEstadisticas();
    }
}
