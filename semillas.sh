#!/bin/bash

# Ruta al archivo JAR
JAR_FILE="target/MaximumIndependentSet-jar-with-dependencies.jar"

# Parámetros fijos (puedes ajustarlos según tus necesidades)
GRAPH_DOCUMENT="inputs/50_20.mis"
NUM_ANTS=10
VERTICES=20

# Nombre del archivo CSV de salida
CSV_FILE="resultados33.csv"

# Iterar sobre los valores de seed desde 1 hasta 1000
for seed in {1..1000}; do
    
    # Ejecutar el comando Java y redirigir la salida al archivo CSV
    salida=$(java -jar $JAR_FILE $GRAPH_DOCUMENT $NUM_ANTS $seed $VERTICES)
    
    # Agregar la fila al archivo CSV
    echo "Ejecutando con $seed: $seed, $salida"
    echo "$seed,$salida" >> $CSV_FILE
done

echo "Proceso completado. Resultados guardados en $CSV_FILE"
