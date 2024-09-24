#!/usr/bin/env bash

# Function to create marker file
create_marker() {
    pomFile=$1
    pomDir=$(dirname "$pomFile")
    markerFile="$pomDir/v3.marker"

    echo "adding marker file ${markerFile}"
    echo "marker file to activate v3 Maven profile" > "$markerFile"
}

for pomFile in $(find . -name pom.xml -print); do
  create_marker "$pomFile"
done

mvn versions:update-parent -DparentVersion=3.1.0 -DgenerateBackupPoms=false
mvn versions:set -DnewVersion=3.1.0-SNAPSHOT -DgenerateBackupPoms=false
mvn rewrite:run
