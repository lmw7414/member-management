#!/bin/bash

SERVICE_NAME="member-management"

echo "Starting ${SERVICE_NAME} service..."
sudo systemctl start ${SERVICE_NAME}

echo
echo "Current status:"
sudo systemctl status ${SERVICE_NAME} --no-pager -n 5