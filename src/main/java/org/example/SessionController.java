
package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Ruleta> ruletas = new HashMap<>();

    private Usuario usuarioActual;
    private String ultimoError = "";

    public SessionController() {
        Usuario admin = new Usuario("admin", "1234", "Administrador");
        Usuario jugador = new Usuario("jugador", "1234", "Jugador");

        usuarios.add(admin);
        usuarios.add(jugador);

        ruletas.put(admin.getUsername(), new Ruleta());
        ruletas.put(jugador.getUsername(), new Ruleta());
    }

    public boolean iniciarSesion(String username, String password) {
        ultimoError = "";

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            ultimoError = "Ingresa tu usuario y contraseña.";
            return false;
        }

        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(username, password)) {
                usuarioActual = usuario;

                ruletas.computeIfAbsent(
                        usuario.getUsername(),
                        clave -> new Ruleta()
                );

                return true;
            }
        }

        ultimoError = "Usuario o contraseña incorrectos.";
        return false;
    }

    public boolean registrarUsuario(
            String username,
            String password,
            String nombre
    ) {
        ultimoError = "";

        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || nombre == null || nombre.isBlank()) {
            ultimoError = "Todos los campos son obligatorios.";
            return false;
        }

        String usuarioLimpio = username.trim();

        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equalsIgnoreCase(usuarioLimpio)) {
                ultimoError = "Ese nombre de usuario ya está registrado.";
                return false;
            }
        }

        Usuario nuevoUsuario = new Usuario(
                usuarioLimpio,
                password,
                nombre.trim()
        );

        usuarios.add(nuevoUsuario);
        ruletas.put(usuarioLimpio, new Ruleta());

        return true;
    }

    public ControladorRuleta crearControladorRuleta() {
        if (usuarioActual == null) {
            throw new IllegalStateException(
                    "Debes iniciar sesión antes de abrir la ruleta."
            );
        }

        Ruleta ruleta = ruletas.computeIfAbsent(
                usuarioActual.getUsername(),
                clave -> new Ruleta()
        );

        return new ControladorRuleta(usuarioActual, ruleta);
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public Ruleta getRuletaActual() {
        if (usuarioActual == null) {
            throw new IllegalStateException(
                    "No hay ningún usuario con sesión iniciada."
            );
        }

        return ruletas.computeIfAbsent(
                usuarioActual.getUsername(),
                clave -> new Ruleta()
        );
    }

    public String getUltimoError() {
        return ultimoError;
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
        ultimoError = "";
    }
}
