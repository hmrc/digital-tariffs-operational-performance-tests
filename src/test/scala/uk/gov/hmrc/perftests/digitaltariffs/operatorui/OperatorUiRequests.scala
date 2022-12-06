/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus._
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiRequests extends DigitalTariffsPerformanceTestRunner {

  private val homePage = s"$operatorUiBaseUrl/operator-dashboard-classification"

  def getStartPage: HttpRequestBuilder =
    http("GET Home Page")
      .get(homePage)
      .check(status.is(OK.code()))
      .check(currentLocation.is(homePage))

  def getGatewayQueue: HttpRequestBuilder =
    http("Gateway Queue")
      .get(s"$operatorUiBaseUrl/gateway-cases")
      .disableFollowRedirect
      .check(status.is(OK.code()))

  def getCaseTraderDetails: HttpRequestBuilder =
    http("GET View Case Trader Details")
      .get(operatorUiBaseUrl + "/cases/v2/${case_reference}/atar")
      .check(status.is(OK.code()))

  def getFindValidCaseReference: HttpRequestBuilder =
    http("GET Find Valid Case Reference")
      .get(
        s"$operatorUiBaseUrl/search?case_source=.*&commodity_code=&decision_details=" +
          s"&keyword%5B0%5D=&application_type%5B0%5D=BTI&status%5B4%5D=NEW&selectedTab=details#advanced_search-results_and_filters"
      )
      .check(status.is(OK.code()))
      .check(
        css("#advanced_search_results-row-0-reference-link").find.saveAs("case_reference")
      )
}
