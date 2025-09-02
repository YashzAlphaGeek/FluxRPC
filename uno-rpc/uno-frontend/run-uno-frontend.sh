#!/bin/bash
# Run React frontend for Uno game

echo "Starting React frontend..."
cd "$(dirname "$0")" || exit 1

# Paths
PROTO_DIR="../uno-backend/src/main/proto"
GRPC_OUT_DIR="./src/grpc"

# Create grpc output directory if it doesn't exist
mkdir -p "$GRPC_OUT_DIR"

# Generate gRPC code from proto files if needed
PROTO_FILE="$PROTO_DIR/uno.proto"
JS_OUT="$GRPC_OUT_DIR/uno_pb.js"
GRPC_OUT="$GRPC_OUT_DIR/uno_grpc_web_pb.js"

# Only regenerate if proto file is newer than generated files
if [ ! -f "$JS_OUT" ] || [ "$PROTO_FILE" -nt "$JS_OUT" ] || [ ! -f "$GRPC_OUT" ] || [ "$PROTO_FILE" -nt "$GRPC_OUT" ]; then
    echo "Generating gRPC code from proto files..."
    protoc -I="$PROTO_DIR" \
      "$PROTO_FILE" \
      --js_out=import_style=commonjs:"$GRPC_OUT_DIR" \
      --grpc-web_out=import_style=commonjs,mode=grpcwebtext:"$GRPC_OUT_DIR"
else
    echo "gRPC code is up-to-date."
fi

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing npm dependencies..."
    npm install
fi

# Start React dev server
npm start
