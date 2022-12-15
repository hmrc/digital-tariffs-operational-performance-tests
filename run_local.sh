#!/bin/bash

./run_format_and_deps.sh

sbt Gatling/test -DrunLocal=true
