#!/usr/bin/env bash

# write marker files
for pomFile in $(find . -name pom.xml -print)
do
    pomDir=$(dirname $pomFile)
    echo $pomDir | grep -v pipeline-resources >/dev/null
    if [ $? -eq 0 ]; then
      echo "adding marker file ${pomDir}/v3.marker"
      echo "marker file to activate v3 Maven profile" > $pomDir/v3.marker
    fi
done
