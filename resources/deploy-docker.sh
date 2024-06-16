#!/usr/bin/env bash

# shellcheck disable=SC1091
source "$(realpath "$(dirname "$0")")/utils.sh"
JSON_FILE="$(realpath "$(dirname "$0")")/config.json"
SERVICE_NAME="$1"

check_service_exists() {
  local service_name="$1"
  local service_to_check

  service_to_check=$(jq -r --arg service "$service_name" '.[$project][] | select(. == $service)' "$JSON_FILE")

  if [[ -z "$service_to_check" ]]; then
    error "$service_name does not exist in config. Exiting."
  fi
}

main() {
  success "Init Script execution started."

  check_service_exists "$SERVICE_NAME"

  cd "/opt/composefiles/$SERVICE_NAME" || error "Failed to change to service directory"

  docker compose down || error "Failed to stop and remove containers"

  docker compose up -d --remove-orphans || error "Failed to start containers"

  success "Init Script execution complete."
}

main "$SERVICE_NAME"
