
package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController {

    private static final SessionController INSTANCIA =
            new SessionController();

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Ruleta> ruletas = new HashMap<>();

    private Usuario usuarioActual;

    private SessionController() {
        usuarios.add(
                new Usuario("admin", "1234", "Administrador")
        );
        usuarios.add(
                new Usuario("jugador", "1234", "Jugador")
        );
    }

    public static SessionController getInstancia() {
        return INSTANCIA;
    }

    public Usuario iniciarSesion(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(username, password)) {
                usuarioActual = usuario;
                return usuario;
            }
        }

        return null;
    }

    public boolean registrarUsuario(
            String username,
            String password,
            String nombre
    ) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios."
            );
        }

        String usuarioLimpio = username.trim();

        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equalsIgnoreCase(usuarioLimpio)) {
                return false;
            }
        }

        usuarios.add(
                new Usuario(
                        usuarioLimpio,
                        password,
                        nombre.trim()
                )
        );

        return true;
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
                username -> new Ruleta()
        );
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
}
