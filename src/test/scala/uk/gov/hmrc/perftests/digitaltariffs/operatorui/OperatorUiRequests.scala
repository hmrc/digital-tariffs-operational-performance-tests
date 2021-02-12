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

  def getCarsQueue: HttpRequestBuilder = {
    http("Cars Queue")
      .get(s"$operatorUiBaseUrl/queues/cars")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getElmQueue: HttpRequestBuilder = {
    http("Elm Queue")
      .get(s"$operatorUiBaseUrl/queues/elm")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getActQueue: HttpRequestBuilder = {
    http("ACT Queue")
      .get(s"$operatorUiBaseUrl/queues/act")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCapQueue: HttpRequestBuilder = {
    http("CAP Queue")
      .get(s"$operatorUiBaseUrl/queues/cap")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getMyCases: HttpRequestBuilder = {
    http("My cases")
      .get(s"$operatorUiBaseUrl/queues/my-cases")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCaseTraderDetails: HttpRequestBuilder = {
    http(s"View Case Trader Details")
      .get(operatorUiBaseUrl + "/cases/v2/${case_reference}/atar")
      .check(status.is(200))
  }

  def getCaseApplicationDetails: HttpRequestBuilder = {
    http(s"View Case Application Details")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/item")
      .check(status.is(200))
  }

  def getCaseAttachments: HttpRequestBuilder = {
    http("View Case ${case_reference} Attachments")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/attachments")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCaseActivity: HttpRequestBuilder = {
    http("View Case ${case_reference} Activity")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/activity")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCaseKeywords: HttpRequestBuilder = {
    http("View Case ${case_reference} Keywords")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/keywords")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def getCaseRuling: HttpRequestBuilder = {
    http("View Case ${case_reference} Ruling")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/ruling")
      .disableFollowRedirect
      .check(status.is(200))
  }

  def searchPage: HttpRequestBuilder = {
    http("Search Page")
      .get(s"$operatorUiBaseUrl/search")
      .check(status.is(200))
  }

  def getQueryResultPage: HttpRequestBuilder = {
    http("Search Results")
      .get(s"$operatorUiBaseUrl/search?trader_name=John+Lewis&commodity_code=&decision_details=&keyword%5B0%5D=&live_rulings_only=true")
      .check(status.is(200))
  }

  def findValidCaseReference: HttpRequestBuilder = {
    http("Find Valid Case Reference")
      .get(s"$operatorUiBaseUrl/search?trader_name=.*&commodity_code=&decision_details=&keyword%5B0%5D=&application_type%5B0%5D=BTI&status%5B4%5D=NEW&selectedTab=details#advanced_search-results_and_filters")
      .check(status.is(200))
      .check(css("a#advanced_search_results-row-0-reference").find.saveAs("case_reference"))
  }

}
