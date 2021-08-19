import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test = Seq(
    "com.github.nscala-time" %% "nscala-time"               % "2.28.0" % Test,
    "com.typesafe"           %  "config"                    % "1.3.3" % Test,
    "com.typesafe.play"      %% "play-json"                 % "2.6.10" % Test,
    "io.gatling.highcharts"  %  "gatling-charts-highcharts" % gatlingVersion % Test,
    "io.gatling"             %  "gatling-test-framework"    % gatlingVersion % Test,
    "uk.gov.hmrc"            %% "performance-test-runner"   % "4.11.0" % Test
  )
}
