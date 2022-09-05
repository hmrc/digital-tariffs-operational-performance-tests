#!/bin/bash

sbt clean scalafmtAll scalastyleAll compile test:compile dependencyUpdates
