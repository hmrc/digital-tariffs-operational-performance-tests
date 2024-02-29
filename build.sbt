ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "digital-tariffs-operational-performance-tests",
    version := "0.1.0",
    scalacOptions += "-feature",
    libraryDependencies ++= Dependencies(),
    // Enabling sbt-auto-build plugin provides DefaultBuildSettings with default `testOptions` from `sbt-settings` plugin.
    // These testOptions are not compatible with `sbt Gatling/test`. So we have to override testOptions here.
    Test / testOptions := Seq.empty
  )

addCommandAlias("scalafmtAll", "all scalafmtSbt scalafmt Test/scalafmt")
addCommandAlias("scalastyleAll", "all scalastyle Test/scalastyle")
