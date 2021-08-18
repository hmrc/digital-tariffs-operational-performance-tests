
name := "digital-tariffs-operational-performance-tests"

version := "1.0"

scalaVersion := "2.12.14"

val gatlingVersion = "3.4.2"


libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time"               % "2.28.0",
  "com.typesafe"           %  "config"                    % "1.3.3",
  "com.typesafe.play"      %% "play-json"                 % "2.6.10",
  "io.gatling.highcharts"  %  "gatling-charts-highcharts" % gatlingVersion % Test,
  "io.gatling"             %  "gatling-test-framework"    % gatlingVersion % Test,
  "uk.gov.hmrc"            %% "performance-test-runner"   % "4.11.0"
)

enablePlugins(GatlingPlugin)

parallelExecution in Test := false

resolvers += "hmrc-releases" at "https://artefacts.tax.service.gov.uk/artifactory/hmrc-releases/"

