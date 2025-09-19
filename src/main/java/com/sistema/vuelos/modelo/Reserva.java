package com.sistema.vuelos.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Clase que representa una reserva en el sistema
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class Reserva {
    private String codigoReserva;
    private Vuelo vuelo;
    private Pasajero pasajero;
    private LocalDateTime fechaReserva;
    private EstadoReserva estado;
    private double precioFinal;
    private String observaciones;

    public enum EstadoReserva {
        CONFIRMADA, PENDIENTE, CANCELADA, COMPLETADA
    }

    public Reserva(Vuelo vuelo, Pasajero pasajero) {
        this.codigoReserva = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.fechaReserva = LocalDateTime.now();
        this.estado = EstadoReserva.CONFIRMADA;
        this.precioFinal = calcularPrecioFinal();
        this.observaciones = "";
    }

    // Getters y Setters
    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Métodos de negocio
    private double calcularPrecioFinal() {
        double precioBase = vuelo.getPrecio();

        // Aplicar descuentos según tipo de pasajero
        switch (pasajero.getTipo()) {
            case NIÑO:
                precioBase *= 0.75; // 25% descuento para niños
                break;
            case BEBE:
                precioBase *= 0.10; // 90% descuento para bebés
                break;
            case ADULTO_MAYOR:
                precioBase *= 0.85; // 15% descuento para adultos mayores
                break;
            default:
                // Adulto - precio normal
                break;
        }

        return precioBase;
    }

    public void cancelarReserva() {
        this.estado = EstadoReserva.CANCELADA;
        if (vuelo != null) {
            vuelo.removerPasajero(pasajero);
        }
    }

    public void confirmarReserva() {
        this.estado = EstadoReserva.CONFIRMADA;
        if (vuelo != null && vuelo.tieneCapacidad()) {
            vuelo.agregarPasajero(pasajero);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s - %s - %s - %s - %.2f€ - %s",
                codigoReserva, pasajero.getNombreCompleto(),
                vuelo.getCodigoVuelo(), fechaReserva.format(formatter),
                precioFinal, estado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Reserva reserva = (Reserva) obj;
        return codigoReserva.equals(reserva.codigoReserva);
    }

    @Override
    public int hashCode() {
        return codigoReserva.hashCode();
    }
}
