package com.sistema.vuelos.vista;

import com.sistema.vuelos.controlador.SistemaVuelos;
import com.sistema.vuelos.modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana principal del sistema de gestión de vuelos
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class VentanaPrincipal extends JFrame implements ActionListener {

    private SistemaVuelos sistema;
    private JTabbedPane tabbedPane;
    private JTable tablaVuelos, tablaPasajeros, tablaReservas;
    private DefaultTableModel modelVuelos, modelPasajeros, modelReservas;

    // Componentes para gestión de vuelos
    private JTextField txtCodigoVuelo, txtOrigen, txtDestino, txtAerolinea, txtCapacidad, txtPrecio;
    private JComboBox<String> comboEstadoVuelo;

    // Componentes para gestión de pasajeros
    private JTextField txtDni, txtNombre, txtApellidos, txtEmail, txtTelefono;
    private JComboBox<Pasajero.TipoPasajero> comboTipoPasajero;

    // Componentes para reservas
    private JComboBox<String> comboVueloReserva, comboPasajeroReserva;
    private JTextField txtCodigoReserva;

    public VentanaPrincipal() {
        sistema = SistemaVuelos.getInstancia();
        inicializarInterfaz();
        cargarDatos();
    }

    private void inicializarInterfaz() {
        setTitle("Sistema de Gestión de Vuelos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Vuelos", crearPanelVuelos());
        tabbedPane.addTab("Pasajeros", crearPanelPasajeros());
        tabbedPane.addTab("Reservas", crearPanelReservas());
        tabbedPane.addTab("Estadísticas", crearPanelEstadisticas());

        add(tabbedPane, BorderLayout.CENTER);

        // Configurar ventana
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }

    private JPanel crearPanelVuelos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior - Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Vuelos"));
        panelFormulario.setBackground(new Color(240, 248, 255));

        // Código de vuelo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigoVuelo = new JTextField(10);
        panelFormulario.add(txtCodigoVuelo, gbc);

        // Origen
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Origen:"), gbc);
        gbc.gridx = 3;
        txtOrigen = new JTextField(10);
        panelFormulario.add(txtOrigen, gbc);

        // Destino
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1;
        txtDestino = new JTextField(10);
        panelFormulario.add(txtDestino, gbc);

        // Aerolínea
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Aerolínea:"), gbc);
        gbc.gridx = 3;
        txtAerolinea = new JTextField(10);
        panelFormulario.add(txtAerolinea, gbc);

        // Capacidad y Precio
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        txtCapacidad = new JTextField(10);
        panelFormulario.add(txtCapacidad, gbc);

        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 3;
        txtPrecio = new JTextField(10);
        panelFormulario.add(txtPrecio, gbc);

        // Estado
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        comboEstadoVuelo = new JComboBox<>(
                new String[] { "PROGRAMADO", "EN_VUELO", "ATERRIZADO", "CANCELADO", "RETRASADO" });
        panelFormulario.add(comboEstadoVuelo, gbc);

        // Botones
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(crearBoton("Agregar Vuelo", "agregar_vuelo"));
        panelBotones.add(crearBoton("Buscar Vuelo", "buscar_vuelo"));
        panelBotones.add(crearBoton("Eliminar Vuelo", "eliminar_vuelo"));
        panelBotones.add(crearBoton("Actualizar", "actualizar_vuelos"));
        panelFormulario.add(panelBotones, gbc);

        // Tabla de vuelos
        String[] columnasVuelos = { "Código", "Ruta", "Aerolínea", "Fecha Salida", "Capacidad", "Precio", "Estado" };
        modelVuelos = new DefaultTableModel(columnasVuelos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVuelos = new JTable(modelVuelos);
        tablaVuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVuelos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarVuelo();
            }
        });

        JScrollPane scrollVuelos = new JScrollPane(tablaVuelos);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollVuelos, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelPasajeros() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior - Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Pasajeros"));
        panelFormulario.setBackground(new Color(248, 255, 240));

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelFormulario.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(10);
        panelFormulario.add(txtDni, gbc);

        // Nombre
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        txtNombre = new JTextField(10);
        panelFormulario.add(txtNombre, gbc);

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        txtApellidos = new JTextField(10);
        panelFormulario.add(txtApellidos, gbc);

        // Email
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        txtEmail = new JTextField(10);
        panelFormulario.add(txtEmail, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(10);
        panelFormulario.add(txtTelefono, gbc);

        // Tipo de pasajero
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 3;
        comboTipoPasajero = new JComboBox<>(Pasajero.TipoPasajero.values());
        panelFormulario.add(comboTipoPasajero, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(crearBoton("Agregar Pasajero", "agregar_pasajero"));
        panelBotones.add(crearBoton("Buscar Pasajero", "buscar_pasajero"));
        panelBotones.add(crearBoton("Eliminar Pasajero", "eliminar_pasajero"));
        panelBotones.add(crearBoton("Actualizar", "actualizar_pasajeros"));
        panelFormulario.add(panelBotones, gbc);

        // Tabla de pasajeros
        String[] columnasPasajeros = { "DNI", "Nombre", "Apellidos", "Email", "Teléfono", "Tipo" };
        modelPasajeros = new DefaultTableModel(columnasPasajeros, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPasajeros = new JTable(modelPasajeros);
        tablaPasajeros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPasajeros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarPasajero();
            }
        });

        JScrollPane scrollPasajeros = new JScrollPane(tablaPasajeros);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollPasajeros, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior - Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Reservas"));
        panelFormulario.setBackground(new Color(255, 248, 240));

        // Código de reserva
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelFormulario.add(new JLabel("Código Reserva:"), gbc);
        gbc.gridx = 1;
        txtCodigoReserva = new JTextField(10);
        panelFormulario.add(txtCodigoReserva, gbc);

        // Vuelo
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Vuelo:"), gbc);
        gbc.gridx = 3;
        comboVueloReserva = new JComboBox<>();
        panelFormulario.add(comboVueloReserva, gbc);

        // Pasajero
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Pasajero:"), gbc);
        gbc.gridx = 1;
        comboPasajeroReserva = new JComboBox<>();
        panelFormulario.add(comboPasajeroReserva, gbc);

        // Botones
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(crearBoton("Crear Reserva", "crear_reserva"));
        panelBotones.add(crearBoton("Cancelar Reserva", "cancelar_reserva"));
        panelBotones.add(crearBoton("Buscar Reserva", "buscar_reserva"));
        panelBotones.add(crearBoton("Actualizar", "actualizar_reservas"));
        panelFormulario.add(panelBotones, gbc);

        // Tabla de reservas
        String[] columnasReservas = { "Código", "Pasajero", "Vuelo", "Fecha Reserva", "Precio", "Estado" };
        modelReservas = new DefaultTableModel(columnasReservas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReservas = new JTable(modelReservas);
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollReservas = new JScrollPane(tablaReservas);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollReservas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 248, 255));

        // Estadísticas generales
        JPanel panelGeneral = crearPanelEstadistica("Estadísticas Generales",
                "Total Vuelos: " + sistema.getTotalVuelos() + "\n" +
                        "Total Pasajeros: " + sistema.getTotalPasajeros() + "\n" +
                        "Total Reservas: " + sistema.getTotalReservas() + "\n" +
                        "Reservas Activas: " + sistema.getReservasActivas() + "\n" +
                        "Ingresos Totales: " + String.format("%.2f€", sistema.getIngresosTotales()));

        // Vuelos disponibles
        JPanel panelDisponibles = crearPanelEstadistica("Vuelos Disponibles",
                "Vuelos con disponibilidad: " + sistema.getVuelosConDisponibilidad().size() + "\n" +
                        "Vuelos completos: " + sistema.getVuelosCompletos().size());

        // Por aerolínea
        StringBuilder aerolineas = new StringBuilder("Vuelos por Aerolínea:\n");
        sistema.getEstadisticasPorAerolinea().forEach(
                (aerolinea, cantidad) -> aerolineas.append(aerolinea).append(": ").append(cantidad).append("\n"));

        JPanel panelAerolineas = crearPanelEstadistica("Distribución por Aerolínea", aerolineas.toString());

        // Botón actualizar
        JPanel panelActualizar = new JPanel(new BorderLayout());
        JButton btnActualizar = crearBoton("Actualizar Estadísticas", "actualizar_estadisticas");
        panelActualizar.add(btnActualizar, BorderLayout.CENTER);

        panel.add(panelGeneral);
        panel.add(panelDisponibles);
        panel.add(panelAerolineas);
        panel.add(panelActualizar);

        return panel;
    }

    private JPanel crearPanelEstadistica(String titulo, String contenido) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setBackground(Color.WHITE);

        JTextArea texto = new JTextArea(contenido);
        texto.setEditable(false);
        texto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        texto.setBackground(Color.WHITE);

        panel.add(texto, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBoton(String texto, String comando) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
        boton.addActionListener(this);
        return boton;
    }

    private void cargarDatos() {
        cargarVuelos();
        cargarPasajeros();
        cargarReservas();
        actualizarCombosReservas();
    }

    private void cargarVuelos() {
        modelVuelos.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Vuelo vuelo : sistema.getVuelos()) {
            Object[] fila = {
                    vuelo.getCodigoVuelo(),
                    vuelo.getRuta(),
                    vuelo.getAerolinea(),
                    vuelo.getFechaHoraSalida().format(formatter),
                    vuelo.getCapacidadDisponible() + "/" + vuelo.getCapacidadTotal(),
                    String.format("%.2f€", vuelo.getPrecio()),
                    vuelo.getEstado()
            };
            modelVuelos.addRow(fila);
        }
    }

    private void cargarPasajeros() {
        modelPasajeros.setRowCount(0);

        for (Pasajero pasajero : sistema.getPasajeros()) {
            Object[] fila = {
                    pasajero.getDni(),
                    pasajero.getNombre(),
                    pasajero.getApellidos(),
                    pasajero.getEmail(),
                    pasajero.getTelefono(),
                    pasajero.getTipo()
            };
            modelPasajeros.addRow(fila);
        }
    }

    private void cargarReservas() {
        modelReservas.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Reserva reserva : sistema.getReservas()) {
            Object[] fila = {
                    reserva.getCodigoReserva(),
                    reserva.getPasajero().getNombreCompleto(),
                    reserva.getVuelo().getCodigoVuelo(),
                    reserva.getFechaReserva().format(formatter),
                    String.format("%.2f€", reserva.getPrecioFinal()),
                    reserva.getEstado()
            };
            modelReservas.addRow(fila);
        }
    }

    private void actualizarCombosReservas() {
        comboVueloReserva.removeAllItems();
        for (Vuelo vuelo : sistema.getVuelos()) {
            comboVueloReserva.addItem(vuelo.getCodigoVuelo());
        }

        comboPasajeroReserva.removeAllItems();
        for (Pasajero pasajero : sistema.getPasajeros()) {
            comboPasajeroReserva.addItem(pasajero.getDni() + " - " + pasajero.getNombreCompleto());
        }
    }

    private void seleccionarVuelo() {
        int fila = tablaVuelos.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modelVuelos.getValueAt(fila, 0);
            Vuelo vuelo = sistema.buscarVueloPorCodigo(codigo);
            if (vuelo != null) {
                txtCodigoVuelo.setText(vuelo.getCodigoVuelo());
                txtOrigen.setText(vuelo.getOrigen());
                txtDestino.setText(vuelo.getDestino());
                txtAerolinea.setText(vuelo.getAerolinea());
                txtCapacidad.setText(String.valueOf(vuelo.getCapacidadTotal()));
                txtPrecio.setText(String.valueOf(vuelo.getPrecio()));
                comboEstadoVuelo.setSelectedItem(vuelo.getEstado().toString());
            }
        }
    }

    private void seleccionarPasajero() {
        int fila = tablaPasajeros.getSelectedRow();
        if (fila >= 0) {
            String dni = (String) modelPasajeros.getValueAt(fila, 0);
            Pasajero pasajero = sistema.buscarPasajeroPorDni(dni);
            if (pasajero != null) {
                txtDni.setText(pasajero.getDni());
                txtNombre.setText(pasajero.getNombre());
                txtApellidos.setText(pasajero.getApellidos());
                txtEmail.setText(pasajero.getEmail());
                txtTelefono.setText(pasajero.getTelefono());
                comboTipoPasajero.setSelectedItem(pasajero.getTipo());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "agregar_vuelo":
                agregarVuelo();
                break;
            case "buscar_vuelo":
                buscarVuelo();
                break;
            case "eliminar_vuelo":
                eliminarVuelo();
                break;
            case "actualizar_vuelos":
                cargarVuelos();
                break;
            case "agregar_pasajero":
                agregarPasajero();
                break;
            case "buscar_pasajero":
                buscarPasajero();
                break;
            case "eliminar_pasajero":
                eliminarPasajero();
                break;
            case "actualizar_pasajeros":
                cargarPasajeros();
                break;
            case "crear_reserva":
                crearReserva();
                break;
            case "cancelar_reserva":
                cancelarReserva();
                break;
            case "buscar_reserva":
                buscarReserva();
                break;
            case "actualizar_reservas":
                cargarReservas();
                break;
            case "actualizar_estadisticas":
                actualizarEstadisticas();
                break;
        }
    }

    private void agregarVuelo() {
        try {
            String codigo = txtCodigoVuelo.getText().trim();
            String origen = txtOrigen.getText().trim();
            String destino = txtDestino.getText().trim();
            String aerolinea = txtAerolinea.getText().trim();
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (codigo.isEmpty() || origen.isEmpty() || destino.isEmpty() || aerolinea.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear vuelo con fecha por defecto (mañana a las 8:00)
            Vuelo nuevoVuelo = new Vuelo(codigo, origen, destino,
                    java.time.LocalDateTime.now().plusDays(1).withHour(8).withMinute(0),
                    java.time.LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
                    aerolinea, capacidad, precio);

            sistema.agregarVuelo(nuevoVuelo);
            cargarVuelos();
            actualizarCombosReservas();

            JOptionPane.showMessageDialog(this, "Vuelo agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioVuelo();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para capacidad y precio.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarVuelo() {
        String codigo = txtCodigoVuelo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de vuelo para buscar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Vuelo vuelo = sistema.buscarVueloPorCodigo(codigo);
        if (vuelo != null) {
            // Seleccionar en la tabla
            for (int i = 0; i < modelVuelos.getRowCount(); i++) {
                if (modelVuelos.getValueAt(i, 0).equals(codigo)) {
                    tablaVuelos.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el vuelo con código: " + codigo, "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarVuelo() {
        String codigo = txtCodigoVuelo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de vuelo para eliminar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el vuelo " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (sistema.eliminarVuelo(codigo)) {
                cargarVuelos();
                actualizarCombosReservas();
                limpiarFormularioVuelo();
                JOptionPane.showMessageDialog(this, "Vuelo eliminado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el vuelo para eliminar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarPasajero() {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            Pasajero.TipoPasajero tipo = (Pasajero.TipoPasajero) comboTipoPasajero.getSelectedItem();

            if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pasajero nuevoPasajero = new Pasajero(dni, nombre, apellidos, email, telefono, tipo);
            sistema.agregarPasajero(nuevoPasajero);
            cargarPasajeros();
            actualizarCombosReservas();

            JOptionPane.showMessageDialog(this, "Pasajero agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioPasajero();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar pasajero: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPasajero() {
        String dni = txtDni.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pasajero pasajero = sistema.buscarPasajeroPorDni(dni);
        if (pasajero != null) {
            // Seleccionar en la tabla
            for (int i = 0; i < modelPasajeros.getRowCount(); i++) {
                if (modelPasajeros.getValueAt(i, 0).equals(dni)) {
                    tablaPasajeros.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el pasajero con DNI: " + dni, "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarPasajero() {
        String dni = txtDni.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el pasajero con DNI " + dni + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (sistema.eliminarPasajero(dni)) {
                cargarPasajeros();
                actualizarCombosReservas();
                limpiarFormularioPasajero();
                JOptionPane.showMessageDialog(this, "Pasajero eliminado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el pasajero para eliminar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void crearReserva() {
        String codigoVuelo = (String) comboVueloReserva.getSelectedItem();
        String pasajeroInfo = (String) comboPasajeroReserva.getSelectedItem();

        if (codigoVuelo == null || pasajeroInfo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un vuelo y un pasajero.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dniPasajero = pasajeroInfo.split(" - ")[0];

        Reserva reserva = sistema.crearReserva(codigoVuelo, dniPasajero);
        if (reserva != null) {
            cargarReservas();
            cargarVuelos(); // Actualizar disponibilidad
            JOptionPane.showMessageDialog(this,
                    "Reserva creada correctamente.\nCódigo: " + reserva.getCodigoReserva() +
                            "\nPrecio: " + String.format("%.2f€", reserva.getPrecioFinal()),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo crear la reserva. Verifique que el vuelo tenga disponibilidad.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarReserva() {
        String codigo = txtCodigoReserva.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de reserva para cancelar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea cancelar la reserva " + codigo + "?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (sistema.cancelarReserva(codigo)) {
                cargarReservas();
                cargarVuelos(); // Actualizar disponibilidad
                txtCodigoReserva.setText("");
                JOptionPane.showMessageDialog(this, "Reserva cancelada correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la reserva para cancelar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarReserva() {
        String codigo = txtCodigoReserva.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de reserva para buscar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reserva reserva = sistema.buscarReservaPorCodigo(codigo);
        if (reserva != null) {
            // Seleccionar en la tabla
            for (int i = 0; i < modelReservas.getRowCount(); i++) {
                if (modelReservas.getValueAt(i, 0).equals(codigo)) {
                    tablaReservas.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la reserva con código: " + codigo, "No encontrada",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarEstadisticas() {
        tabbedPane.setComponentAt(3, crearPanelEstadisticas());
        tabbedPane.setSelectedIndex(3);
    }

    private void limpiarFormularioVuelo() {
        txtCodigoVuelo.setText("");
        txtOrigen.setText("");
        txtDestino.setText("");
        txtAerolinea.setText("");
        txtCapacidad.setText("");
        txtPrecio.setText("");
        comboEstadoVuelo.setSelectedIndex(0);
    }

    private void limpiarFormularioPasajero() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        comboTipoPasajero.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new VentanaPrincipal().setVisible(true);
        });
    }
}
