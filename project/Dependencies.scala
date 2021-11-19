import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test = Seq(
    "com.github.nscala-time" %% "nscala-time"               % "2.30.0" % Test,
    "com.typesafe"           %  "config"                    % "1.4.1" % Test,
    "com.typesafe.play"      %% "play-json"                 % "2.9.2" % Test,
    "io.gatling.highcharts"  %  "gatling-charts-highcharts" % gatlingVersion % Test,
    "io.gatling"             %  "gatling-test-framework"    % gatlingVersion % Test,
    "uk.gov.hmrc"            %% "performance-test-runner"   % "5.0.0" % Test
  )
}
