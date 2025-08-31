#!/bin/bash

# Find running Java processes
JAVA_PIDS=$(pgrep -f java)

if [ -n "$JAVA_PIDS" ]; then
    echo "Killing running Java processes: $JAVA_PIDS"
    kill -9 $JAVA_PIDS
else
    echo "No running Java processes found."
fi

# Run Maven commands
echo "Running 'mvn clean install'..."
mvn clean install

echo "Running 'mvn spring-boot:run'..."
mvn spring-boot:run
