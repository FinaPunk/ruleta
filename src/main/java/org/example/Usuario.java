
package org.example;

public class Usuario {

    private String username;
    private String password;
    private String nombre;

    public Usuario(String username, String password, String nombre) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de usuario es obligatorio."
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria."
            );
        }

        this.username = username.trim();
        this.password = password;
        setNombre(nombre);
    }

    public Usuario() {
        this("invitado", "invitado", "Invitado");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public boolean validarCredenciales(
            String username,
            String password
    ) {
        return username != null
                && password != null
                && this.username.equalsIgnoreCase(username.trim())
                && this.password.equals(password);
    }
}
