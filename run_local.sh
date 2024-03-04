#!/bin/bash

./run_format_and_deps.sh

sbt -Dperftest.runSmokeTest=false -DrunLocal=true Gatling/test
