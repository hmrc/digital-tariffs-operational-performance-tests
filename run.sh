#!/bin/bash
sbt -Dperftest.runSmokeTest=false -DrunLocal=false Gatling/test
