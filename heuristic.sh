#!/bin/bash

# Ruta al archivo JAR
JAR_FILE="target/MaximumIndependentSet-jar-with-dependencies.jar"

# Parámetros fijos (puedes ajustarlos según tus necesidades)
GRAPH_DOCUMENT="inputs/100_33.mis"
NUM_ANTS=10
VERTICES=20


# Iterar sobre los valores de seed desde 1 hasta 1000
for vertice in {33..1}; do
    
    # Ejecutar el comando Java y redirigir la salida al archivo CSV
    salida=$(java -jar $JAR_FILE $GRAPH_DOCUMENT $NUM_ANTS 5 $vertice)
    
    # Agregar la fila al archivo CSV
    echo "Ejecutando con $vertice vertices: $salida"
done

echo "Proceso completado. Resultados guardados en $CSV_FILE"
