import io.gatling.sbt.GatlingPlugin


name := "digital-tariffs-operational-performance-tests"

version := "1.0"

scalaVersion := "2.11.11"

val gatlingVersion = "2.2.5"

libraryDependencies ++= Seq("io.gatling" % "gatling-core" % "2.2.5",
  "com.github.nscala-time" %% "nscala-time" % "2.22.0",
  "com.typesafe" % "config" % "1.3.3",
  "com.typesafe.play" % "play-json_2.11" % "2.4.3",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion,
  "io.gatling" % "gatling-test-framework" % gatlingVersion,
  "uk.gov.hmrc" %% "performance-test-runner" % "3.3.0"
)

enablePlugins(GatlingPlugin)

parallelExecution in Test := false

resolvers := Seq(Resolver.bintrayRepo("hmrc", "releases"))
