import sbt._

object Dependencies {

  private val gatlingVersion = "3.6.1"

  private val test               = Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion,
    "io.gatling"            % "gatling-test-framework"    % gatlingVersion,
    "uk.gov.hmrc"          %% "performance-test-runner"   % "5.6.0"
  ).map(_ % Test)

  def apply(): Seq[sbt.ModuleID] = test

}
