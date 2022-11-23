import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test: Seq[ModuleID] = Seq(
    "com.github.nscala-time" %% "nscala-time"               % "2.32.0",
    "com.typesafe"            % "config"                    % "1.4.2",
    "com.typesafe.play"      %% "play-json"                 % "2.9.3",
    "io.gatling.highcharts"   % "gatling-charts-highcharts" % gatlingVersion,
    "io.gatling"              % "gatling-test-framework"    % gatlingVersion,
    "uk.gov.hmrc"            %% "performance-test-runner"   % "5.3.0"
  ).map(_ % Test)
}
