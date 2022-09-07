#!/bin/bash

./run_format_and_deps.sh

sbt gatling:test -DrunLocal=true
