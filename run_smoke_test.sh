#!/bin/bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
