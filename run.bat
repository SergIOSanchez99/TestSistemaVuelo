@echo off
echo Compilando Sistema de Gestion de Vuelos...
echo.

REM Crear directorio de salida si no existe
if not exist "out" mkdir out

REM Compilar todas las clases Java
javac -d out -cp "src/main/java" src/main/java/com/sistema/vuelos/modelo/*.java src/main/java/com/sistema/vuelos/controlador/*.java src/main/java/com/sistema/vuelos/vista/*.java

if %errorlevel% neq 0 (
    echo Error al compilar. Verifique que Java este instalado y que todos los archivos esten presentes.
    pause
    exit /b 1
)

echo Compilacion exitosa!
echo.
echo Ejecutando Sistema de Gestion de Vuelos...
echo.

REM Ejecutar
java -cp out com.sistema.vuelos.vista.VentanaPrincipal

pause
