# ✈️ Sistema de Gestión de Vuelos

Un sistema completo de gestión de vuelos desarrollado en Java usando Swing con interfaz gráfica moderna y funcional.

## 📋 Características

- ✅ **Gestión de Vuelos**: Crear, buscar, modificar y eliminar vuelos
- ✅ **Gestión de Pasajeros**: Registro completo de pasajeros con diferentes tipos
- ✅ **Sistema de Reservas**: Crear y cancelar reservas con cálculo automático de precios
- ✅ **Interfaz Gráfica Moderna**: Interfaz intuitiva con pestañas organizadas
- ✅ **Estadísticas en Tiempo Real**: Dashboard con métricas del sistema
- ✅ **Cálculo de Precios**: Descuentos automáticos según tipo de pasajero
- ✅ **Validaciones**: Control de capacidad y validación de datos
- ✅ **Compatibilidad**: Compatible con Java 11+

## 🚀 Cómo ejecutar

### Prerrequisitos

- Java 11 o superior instalado
- Maven (opcional, para compilar)

### Opción 1: Ejecutar directamente

```bash
# Compilar
javac -d out -cp "src/main/java" src/main/java/com/sistema/vuelos/vista/VentanaPrincipal.java src/main/java/com/sistema/vuelos/controlador/SistemaVuelos.java src/main/java/com/sistema/vuelos/modelo/*.java

# Ejecutar
java -cp out com.sistema.vuelos.vista.VentanaPrincipal
```

### Opción 2: Usando Maven

```bash
# Compilar y ejecutar
mvn clean compile exec:java -Dexec.mainClass="com.sistema.vuelos.vista.VentanaPrincipal"

# O crear JAR ejecutable
mvn clean package
java -jar target/sistema-vuelos-1.0.0.jar
```

## 🎮 Cómo usar

### Gestión de Vuelos

1. **Agregar Vuelo**: Completa el formulario con código, origen, destino, aerolínea, capacidad y precio
2. **Buscar Vuelo**: Ingresa el código del vuelo para localizarlo
3. **Eliminar Vuelo**: Selecciona un vuelo y confirma la eliminación

### Gestión de Pasajeros

1. **Registrar Pasajero**: Completa datos personales y selecciona tipo (Adulto, Niño, Bebé, Adulto Mayor)
2. **Buscar Pasajero**: Usa el DNI para localizar un pasajero
3. **Modificar/Eliminar**: Selecciona un pasajero de la tabla para editarlo

### Sistema de Reservas

1. **Crear Reserva**: Selecciona vuelo y pasajero, el sistema calcula el precio automáticamente
2. **Cancelar Reserva**: Ingresa el código de reserva para cancelarla
3. **Ver Reservas**: Consulta todas las reservas en la tabla correspondiente

### Estadísticas

- **Dashboard Completo**: Ve métricas generales, vuelos disponibles y distribución por aerolínea
- **Actualización en Tiempo Real**: Los datos se actualizan automáticamente

## 🏗️ Estructura del proyecto

```
ProyectoJava/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sistema/
│                   └── vuelos/
│                       ├── modelo/
│                       │   ├── Vuelo.java
│                       │   ├── Pasajero.java
│                       │   └── Reserva.java
│                       ├── controlador/
│                       │   └── SistemaVuelos.java
│                       └── vista/
│                           └── VentanaPrincipal.java
├── pom.xml
├── .gitignore
├── LICENSE
└── README.md
```

## 🛠️ Tecnologías utilizadas

- **Java 11+**: Lenguaje de programación
- **Swing**: Biblioteca para interfaz gráfica
- **Maven**: Herramienta de construcción y gestión de dependencias
- **Patrón MVC**: Arquitectura Modelo-Vista-Controlador
- **Patrón Singleton**: Para el controlador principal

## 📝 Funcionalidades implementadas

### Modelo de Datos

- ✅ **Clase Vuelo**: Información completa de vuelos con estados y capacidad
- ✅ **Clase Pasajero**: Datos personales con tipos diferenciados
- ✅ **Clase Reserva**: Sistema de reservas con cálculo automático de precios
- ✅ **Enumeraciones**: Estados de vuelo, tipos de pasajero y estados de reserva

### Lógica de Negocio

- ✅ **Gestión de Capacidad**: Control automático de asientos disponibles
- ✅ **Cálculo de Precios**: Descuentos según tipo de pasajero (Niños 25%, Bebés 90%, Adultos Mayores 15%)
- ✅ **Validaciones**: Control de datos y operaciones seguras
- ✅ **Búsquedas**: Por código, DNI, ruta, fecha, etc.
- ✅ **Estadísticas**: Métricas en tiempo real del sistema

### Interfaz de Usuario

- ✅ **Interfaz por Pestañas**: Organización clara de funcionalidades
- ✅ **Formularios Intuitivos**: Campos organizados y validados
- ✅ **Tablas Interactivas**: Selección y visualización de datos
- ✅ **Mensajes Informativos**: Confirmaciones y alertas claras
- ✅ **Diseño Responsivo**: Adaptable a diferentes tamaños de ventana

## 🎨 Captura de pantalla

El sistema cuenta con una interfaz moderna y organizada:

### Pestañas Principales

- **Vuelos**: Formulario azul claro para gestión de vuelos
- **Pasajeros**: Formulario verde claro para registro de pasajeros
- **Reservas**: Formulario naranja claro para sistema de reservas
- **Estadísticas**: Dashboard con métricas en tiempo real

### Características Visuales

- **Colores Diferenciados**: Cada sección tiene su propio esquema de colores
- **Tablas Interactivas**: Selección de filas con información detallada
- **Botones Organizados**: Agrupados por funcionalidad
- **Formularios Claro**: Campos etiquetados y organizados
- **Mensajes Informativos**: Alertas y confirmaciones contextuales

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar el sistema de vuelos:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Tu Nombre**

- GitHub: [@tuusuario](https://github.com/tuusuario)

## 🙏 Agradecimientos

- Oracle por Java
- La comunidad de desarrolladores Java
- Todos los contribuidores del proyecto

---

## 🚀 Características Avanzadas

- **Datos de Ejemplo**: El sistema incluye vuelos y pasajeros de ejemplo para pruebas
- **Persistencia en Memoria**: Los datos se mantienen durante la ejecución
- **Cálculo Automático**: Precios con descuentos según tipo de pasajero
- **Control de Capacidad**: No permite reservas en vuelos completos
- **Estados de Vuelo**: Programado, En Vuelo, Aterrizado, Cancelado, Retrasado
- **Códigos Únicos**: Generación automática de códigos de reserva

## 📊 Datos de Ejemplo Incluidos

El sistema viene con datos de prueba:

- **4 Vuelos**: Madrid-Barcelona, Barcelona-París, Madrid-Londres, Valencia-Roma
- **3 Pasajeros**: Con diferentes tipos (Adulto, Niño)
- **Aerolíneas**: Iberia, Vueling, British Airways, Ryanair
- **Precios Variados**: Desde 79.99€ hasta 199.99€

---

⭐ ¡No olvides darle una estrella al proyecto si te gusta!
