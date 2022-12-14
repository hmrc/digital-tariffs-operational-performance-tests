#!/bin/bash

./run_format_and_deps.sh

sbt -Dperftest.runSmokeTest=true -DrunLocal=true Gatling/test
