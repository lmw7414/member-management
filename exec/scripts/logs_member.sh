#!/bin/bash

SERVICE_NAME="member-management"

echo "Tailing logs for ${SERVICE_NAME} service (Ctrl+C to exit)..."
sudo journalctl -u ${SERVICE_NAME} -f