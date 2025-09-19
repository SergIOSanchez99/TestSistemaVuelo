package com.sistema.vuelos.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un vuelo en el sistema
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class Vuelo {
    private String codigoVuelo;
    private String origen;
    private String destino;
    private LocalDateTime fechaHoraSalida;
    private LocalDateTime fechaHoraLlegada;
    private String aerolinea;
    private int capacidadTotal;
    private double precio;
    private List<Pasajero> pasajeros;
    private EstadoVuelo estado;

    public enum EstadoVuelo {
        PROGRAMADO, EN_VUELO, ATERRIZADO, CANCELADO, RETRASADO
    }

    public Vuelo(String codigoVuelo, String origen, String destino,
            LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada,
            String aerolinea, int capacidadTotal, double precio) {
        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fechaHoraSalida = fechaHoraSalida;
        this.fechaHoraLlegada = fechaHoraLlegada;
        this.aerolinea = aerolinea;
        this.capacidadTotal = capacidadTotal;
        this.precio = precio;
        this.pasajeros = new ArrayList<>();
        this.estado = EstadoVuelo.PROGRAMADO;
    }

    // Getters y Setters
    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public LocalDateTime getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) {
        this.fechaHoraLlegada = fechaHoraLlegada;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    // Métodos de negocio
    public int getCapacidadDisponible() {
        return capacidadTotal - pasajeros.size();
    }

    public boolean tieneCapacidad() {
        return getCapacidadDisponible() > 0;
    }

    public void agregarPasajero(Pasajero pasajero) {
        if (tieneCapacidad()) {
            pasajeros.add(pasajero);
        }
    }

    public void removerPasajero(Pasajero pasajero) {
        pasajeros.remove(pasajero);
    }

    public String getRuta() {
        return origen + " → " + destino;
    }

    public String getDuracion() {
        long horas = java.time.Duration.between(fechaHoraSalida, fechaHoraLlegada).toHours();
        long minutos = java.time.Duration.between(fechaHoraSalida, fechaHoraLlegada).toMinutes() % 60;
        return horas + "h " + minutos + "m";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s - %s (%s) - %s - %s - %.2f€ - %d/%d asientos",
                codigoVuelo, getRuta(), aerolinea,
                fechaHoraSalida.format(formatter), estado, precio,
                pasajeros.size(), capacidadTotal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vuelo vuelo = (Vuelo) obj;
        return codigoVuelo.equals(vuelo.codigoVuelo);
    }

    @Override
    public int hashCode() {
        return codigoVuelo.hashCode();
    }
}
