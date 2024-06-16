#!/bin/bash

# This script contains utility functions for printing colored output and setting
# the debug mode.

# Colors for printing colored output
readonly COLOR_BLUE='\e[36m'  # Blue
readonly COLOR_RED='\e[31m'   # Red
readonly COLOR_GREEN='\e[32m' # Green
readonly COLOR_RESET='\e[0m' # Reset color

# Debug mode flag
DEBUG_MODE=false


# Prints an informational message with a blue color
#
# Args:
#   $1: The message to print
#
# Examples:
#   info "This is an informational message"
#
info() {
  echo -e "${COLOR_BLUE}=> $1${COLOR_RESET}"
}


# Prints a success message with a green color
#
# Args:
#   $1: The message to print
#
# Examples:
#   success "Operation completed successfully"
#
success() {
  echo -e "${COLOR_GREEN}✔ $1${COLOR_RESET}"
}


# Prints an error message with a red color and exits with a non-zero status code
#
# Args:
#   $1: The error message to print
#   $2: The exit status code (default: 1)
#
# Examples:
#   error "An error occurred"
#   error "An error occurred" 2
#
error() {
  echo -e "${COLOR_RED}✖ $1${COLOR_RESET}"
  exit "${2:-1}"
}

directory_exists() {
  if [[ -d "$1" ]]; then
     info "$1 exists.Proceeding..."
  else
    error "$1 does not exist"
  fi
}

# Check if the script is running in debug mode
if [[ $1 == "--debug" ]]; then
  DEBUG_MODE=true
  shift
fi

# Enable debug mode if the flag is set
if $DEBUG_MODE; then
  set -x
fi
