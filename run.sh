#!/bin/bash

echo "Compilando Sistema de Gestión de Vuelos..."
echo

# Crear directorio de salida si no existe
mkdir -p out

# Compilar todas las clases Java
javac -d out -cp "src/main/java" src/main/java/com/sistema/vuelos/modelo/*.java src/main/java/com/sistema/vuelos/controlador/*.java src/main/java/com/sistema/vuelos/vista/*.java

if [ $? -ne 0 ]; then
    echo "Error al compilar. Verifique que Java esté instalado y que todos los archivos estén presentes."
    exit 1
fi

echo "Compilación exitosa!"
echo
echo "Ejecutando Sistema de Gestión de Vuelos..."
echo

# Ejecutar
java -cp out com.sistema.vuelos.vista.VentanaPrincipal
