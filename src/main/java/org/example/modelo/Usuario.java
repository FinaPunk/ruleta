package org.example.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    private String username;
    private String password;
    private String nombre;
    private final List<Resultado> historialResultados = new ArrayList<>();

    public Usuario(String username, String password, String nombre) {
        setUsername(username);
        setPassword(password);
        setNombre(nombre);
    }

    public Usuario() {
        this("invitado", "invitado", "Invitado");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public boolean validarCredenciales(String username, String password) {
        return username != null
                && password != null
                && this.username.equalsIgnoreCase(username.trim())
                && this.password.equals(password);
    }

    public void agregarResultado(Resultado resultado) {
        if (resultado != null) {
            this.historialResultados.add(resultado);
        }
    }

    public List<Resultado> getHistorialResultados() {
        return Collections.unmodifiableList(historialResultados);
    }
}