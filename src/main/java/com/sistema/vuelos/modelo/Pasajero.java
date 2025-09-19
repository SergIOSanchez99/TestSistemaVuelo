package com.sistema.vuelos.modelo;

/**
 * Clase que representa un pasajero en el sistema
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class Pasajero {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private TipoPasajero tipo;

    public enum TipoPasajero {
        ADULTO, NIÑO, BEBE, ADULTO_MAYOR
    }

    public Pasajero(String dni, String nombre, String apellidos,
            String email, String telefono, TipoPasajero tipo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoPasajero getTipo() {
        return tipo;
    }

    public void setTipo(TipoPasajero tipo) {
        this.tipo = tipo;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s",
                getNombreCompleto(), dni, email, tipo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pasajero pasajero = (Pasajero) obj;
        return dni.equals(pasajero.dni);
    }

    @Override
    public int hashCode() {
        return dni.hashCode();
    }
}
