#!/bin/bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false Gatling/test
