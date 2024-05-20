#!/bin/bash

# Obtén una lista de archivos modificados
modified_files=$(git diff --name-only HEAD~1 HEAD)

for file in $modified_files; do
    # Obtén el mensaje del commit anterior para el archivo
    previous_message=$(git log -1 --pretty=%B -- $file)

    # Reconvertir el archivo para que use LF
    dos2unix $file

    # Agrega el archivo al índice
    git add $file

    # Haz un commit con el mensaje anterior
    git commit -m "$previous_message"
done
