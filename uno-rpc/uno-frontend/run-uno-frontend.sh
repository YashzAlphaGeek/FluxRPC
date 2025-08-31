#!/bin/bash
# Run React frontend for Uno game

echo "Starting React frontend..."
cd "$(dirname "$0")"

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing npm dependencies..."
    npm install
fi

# Start React dev server
npm start
