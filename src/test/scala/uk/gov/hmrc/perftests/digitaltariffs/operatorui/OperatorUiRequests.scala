package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiRequests extends DigitalTariffsPerformanceTestRunner {

  private val homePage = s"$operatorUiBaseUrl/operator-dashboard-classification"

  def getStartPage: HttpRequestBuilder = {
    http("Home Page")
      .get(operatorUiBaseUrl)
      .check(currentLocation.is(homePage))
      .check(status.is(200))
  }

  def getGatewayQueue: HttpRequestBuilder = {
    http("Gateway Queue")
      .get(s"$operatorUiBaseUrl/gateway-cases")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCaseTraderDetails: HttpRequestBuilder = {
    http(s"View Case Trader Details")
      .get(operatorUiBaseUrl + "/cases/v2/${case_reference}/atar")
      .check(status.is(200))
  }

  def findValidCaseReference: HttpRequestBuilder = {
    http("Find Valid Case Reference")
      .get(s"$operatorUiBaseUrl/search?case_source=.*&commodity_code=&decision_details=&keyword%5B0%5D=&application_type%5B0%5D=BTI&status%5B4%5D=NEW&selectedTab=details#advanced_search-results_and_filters")
      .check(status.is(200))
      .check(css("a#advanced_search_results-row-0-reference").find.saveAs("case_reference"))
  }
}
