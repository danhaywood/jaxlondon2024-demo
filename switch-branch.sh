#!/usr/bin/env bash

create_marker() {
    markerFile="$(dirname "$1")/v3.marker"
    echo "adding marker file ${markerFile}"
    echo "marker file to activate v3 Maven profile" > "$markerFile"
}

mvn rewrite:run && \
mvn versions:update-parent -DparentVersion=3.1.0 -DgenerateBackupPoms=false && \
mvn versions:set -DnewVersion=3.1.0-SNAPSHOT -DgenerateBackupPoms=false

find . -name pom.xml -exec bash -c 'create_marker "$0"' {} \;

mvnd install -DskipTests

