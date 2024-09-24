#!/usr/bin/env bash

# Function to display help message
show_help() {
    echo "Usage: $0 [-3 | -2]"
    echo ""
    echo "Options:"
    echo "  -3  Switch to v3 (v3.marker files etc)"
    echo "  -2  Switch back to v2"
    echo "  -h  Show this help message"
}

# Default action
ACTION=""

# Process the command-line options
while getopts ":23h" opt; do
  case $opt in
    2)
      ACTION="v2"
      ;;
    3)
      ACTION="v3"
      ;;
    h)
      show_help
      exit 0
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      show_help
      exit 1
      ;;
  esac
done

# Check if an action has been selected
if [ -z "$ACTION" ]; then
    echo "Error: You must specify either -2 or -3 to indicate the branch"
    show_help
    exit 1
fi

# Function to create marker file
create_marker() {
    pomFile=$1
    pomDir=$(dirname "$pomFile")
    markerFile="$pomDir/v3.marker"

    # Skip directories with pipeline-resources in their path
    echo "$pomDir" | grep -v pipeline-resources >/dev/null
    if [ $? -eq 0 ]; then
        echo "adding marker file ${markerFile}"
        echo "marker file to activate v3 Maven profile" > "$markerFile"
    fi
}

# Function to remove marker file
remove_marker() {
    pomFile=$1
    pomDir=$(dirname "$pomFile")
    markerFile="$pomDir/v3.marker"

    # Skip directories with pipeline-resources in their path
    echo "$pomDir" | grep -v pipeline-resources >/dev/null
    if [ $? -eq 0 ] && [ -f "$markerFile" ]; then
        echo "removing marker file ${markerFile}"
        rm "$markerFile"
    fi
}

# Main logic for either creating or removing marker files
for pomFile in $(find . -name pom.xml -print); do
    if [ "$ACTION" == "v3" ]; then
        create_marker "$pomFile"
    elif [ "$ACTION" == "v2" ]; then
        remove_marker "$pomFile"
    fi
done

