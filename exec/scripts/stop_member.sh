#!/bin/bash

SERVICE_NAME="member-management"

echo "Stopping ${SERVICE_NAME} service..."
sudo systemctl stop ${SERVICE_NAME}

echo
echo "Current status:"
sudo systemctl status ${SERVICE_NAME} --no-pager -n 5