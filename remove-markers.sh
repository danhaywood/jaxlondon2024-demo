#!/usr/bin/env bash

# Function to create marker file
remove_marker() {
    pomFile=$1
    pomDir=$(dirname "$pomFile")
    markerFile="$pomDir/v3.marker"

    if [ -f "$markerFile" ]; then
        echo "removing marker file ${markerFile}"
        rm "$markerFile"
    fi
}


for pomFile in $(find . -name pom.xml -print); do
  remove_marker "$pomFile"
done

