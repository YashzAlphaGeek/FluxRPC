#!/bin/bash

# Fail immediately if any command fails
set -e

# Envoy Docker image version
ENVOY_IMAGE="envoyproxy/envoy:v1.30-latest"

# Local envoy.yaml file path (relative to project root)
ENVOY_CONFIG="$PWD/envoy.yaml"

# Check if envoy.yaml exists
if [ ! -f "$ENVOY_CONFIG" ]; then
  echo "envoy.yaml not found at $ENVOY_CONFIG"
  exit 1
fi

# Run Envoy container
docker run --rm \
  -p 8081:8080 \
  -v "$ENVOY_CONFIG:/etc/envoy/envoy.yaml:ro" \
  $ENVOY_IMAGE
