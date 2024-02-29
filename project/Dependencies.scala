import sbt.*

object Dependencies {

  private val gatlingVersion = "3.6.1"

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"          %% "performance-test-runner"   % "5.6.0",
    "io.gatling"            % "gatling-test-framework"    % gatlingVersion,
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion
  )

  def apply(): Seq[ModuleID] = test

}
