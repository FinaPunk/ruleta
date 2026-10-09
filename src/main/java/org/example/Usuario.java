
package org.example;

public class Usuario {

    private String username;
    private String password;
    private String nombre;

    // Constructor con parámetros
    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        setNombre(nombre);
    }

    // Constructor sin parámetros: usuario invitado
    public Usuario() {
        this("invitado", "", "Invitado");
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    // Setter con validación
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    // Validar credenciales
    public boolean validarCredenciales(String u, String p) {
        return username.equals(u) && password.equals(p);
    }
}
