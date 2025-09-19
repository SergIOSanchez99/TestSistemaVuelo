package com.sistema.vuelos.controlador;

import com.sistema.vuelos.modelo.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador principal del sistema de vuelos
 * Maneja la lógica de negocio y la gestión de datos
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class SistemaVuelos {
    private List<Vuelo> vuelos;
    private List<Pasajero> pasajeros;
    private List<Reserva> reservas;
    private static SistemaVuelos instancia;

    private SistemaVuelos() {
        this.vuelos = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.reservas = new ArrayList<>();
        inicializarDatosEjemplo();
    }

    public static SistemaVuelos getInstancia() {
        if (instancia == null) {
            instancia = new SistemaVuelos();
        }
        return instancia;
    }

    private void inicializarDatosEjemplo() {
        // Crear vuelos de ejemplo
        agregarVuelo(new Vuelo("AV001", "Madrid", "Barcelona",
                LocalDateTime.now().plusDays(1).withHour(8).withMinute(0),
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(30),
                "Iberia", 150, 89.99));

        agregarVuelo(new Vuelo("AV002", "Barcelona", "París",
                LocalDateTime.now().plusDays(2).withHour(14).withMinute(30),
                LocalDateTime.now().plusDays(2).withHour(16).withMinute(45),
                "Vueling", 120, 129.99));

        agregarVuelo(new Vuelo("AV003", "Madrid", "Londres",
                LocalDateTime.now().plusDays(3).withHour(10).withMinute(15),
                LocalDateTime.now().plusDays(3).withHour(12).withMinute(30),
                "British Airways", 200, 199.99));

        agregarVuelo(new Vuelo("AV004", "Valencia", "Roma",
                LocalDateTime.now().plusDays(5).withHour(16).withMinute(0),
                LocalDateTime.now().plusDays(5).withHour(18).withMinute(30),
                "Ryanair", 180, 79.99));

        // Crear pasajeros de ejemplo
        agregarPasajero(new Pasajero("12345678A", "Juan", "García López",
                "juan.garcia@email.com", "600123456", Pasajero.TipoPasajero.ADULTO));

        agregarPasajero(new Pasajero("87654321B", "María", "Rodríguez Martín",
                "maria.rodriguez@email.com", "600654321", Pasajero.TipoPasajero.ADULTO));

        agregarPasajero(new Pasajero("11223344C", "Carlos", "Fernández Ruiz",
                "carlos.fernandez@email.com", "600789012", Pasajero.TipoPasajero.NIÑO));
    }

    // Gestión de Vuelos
    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    public List<Vuelo> getVuelos() {
        return new ArrayList<>(vuelos);
    }

    public Vuelo buscarVueloPorCodigo(String codigo) {
        return vuelos.stream()
                .filter(v -> v.getCodigoVuelo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Vuelo> buscarVuelosPorRuta(String origen, String destino) {
        return vuelos.stream()
                .filter(v -> v.getOrigen().equalsIgnoreCase(origen) &&
                        v.getDestino().equalsIgnoreCase(destino))
                .collect(Collectors.toList());
    }

    public List<Vuelo> buscarVuelosPorFecha(LocalDateTime fecha) {
        return vuelos.stream()
                .filter(v -> v.getFechaHoraSalida().toLocalDate().equals(fecha.toLocalDate()))
                .collect(Collectors.toList());
    }

    public boolean eliminarVuelo(String codigo) {
        return vuelos.removeIf(v -> v.getCodigoVuelo().equals(codigo));
    }

    // Gestión de Pasajeros
    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
    }

    public List<Pasajero> getPasajeros() {
        return new ArrayList<>(pasajeros);
    }

    public Pasajero buscarPasajeroPorDni(String dni) {
        return pasajeros.stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarPasajero(String dni) {
        return pasajeros.removeIf(p -> p.getDni().equals(dni));
    }

    // Gestión de Reservas
    public Reserva crearReserva(String codigoVuelo, String dniPasajero) {
        Vuelo vuelo = buscarVueloPorCodigo(codigoVuelo);
        Pasajero pasajero = buscarPasajeroPorDni(dniPasajero);

        if (vuelo == null || pasajero == null) {
            return null;
        }

        if (!vuelo.tieneCapacidad()) {
            return null;
        }

        Reserva reserva = new Reserva(vuelo, pasajero);
        reservas.add(reserva);
        vuelo.agregarPasajero(pasajero);

        return reserva;
    }

    public List<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        return reservas.stream()
                .filter(r -> r.getCodigoReserva().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Reserva> buscarReservasPorPasajero(String dni) {
        return reservas.stream()
                .filter(r -> r.getPasajero().getDni().equals(dni))
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarReservasPorVuelo(String codigoVuelo) {
        return reservas.stream()
                .filter(r -> r.getVuelo().getCodigoVuelo().equals(codigoVuelo))
                .collect(Collectors.toList());
    }

    public boolean cancelarReserva(String codigoReserva) {
        Reserva reserva = buscarReservaPorCodigo(codigoReserva);
        if (reserva != null) {
            reserva.cancelarReserva();
            return true;
        }
        return false;
    }

    // Estadísticas
    public int getTotalVuelos() {
        return vuelos.size();
    }

    public int getTotalPasajeros() {
        return pasajeros.size();
    }

    public int getTotalReservas() {
        return reservas.size();
    }

    public int getReservasActivas() {
        return (int) reservas.stream()
                .filter(r -> r.getEstado() == Reserva.EstadoReserva.CONFIRMADA)
                .count();
    }

    public double getIngresosTotales() {
        return reservas.stream()
                .filter(r -> r.getEstado() == Reserva.EstadoReserva.CONFIRMADA)
                .mapToDouble(Reserva::getPrecioFinal)
                .sum();
    }

    public Map<String, Integer> getEstadisticasPorAerolinea() {
        return vuelos.stream()
                .collect(Collectors.groupingBy(
                        Vuelo::getAerolinea,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Math::toIntExact)));
    }

    public List<Vuelo> getVuelosConDisponibilidad() {
        return vuelos.stream()
                .filter(Vuelo::tieneCapacidad)
                .collect(Collectors.toList());
    }

    public List<Vuelo> getVuelosCompletos() {
        return vuelos.stream()
                .filter(v -> !v.tieneCapacidad())
                .collect(Collectors.toList());
    }
}
