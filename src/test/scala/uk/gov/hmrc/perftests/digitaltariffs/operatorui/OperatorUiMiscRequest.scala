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
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiCorrespondenceRequest.{operatorUiBaseUrl, saveCsrfToken}

object OperatorUiMiscRequest extends DigitalTariffsPerformanceTestRunner {

  def getMiscCase: HttpRequestBuilder =
    http("GET Misc tab")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_miscellaneous_tab")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getCreateMiscCase: HttpRequestBuilder =
    http("GET to Create Misc page")
      .get(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postCreateNewMiscCase: HttpRequestBuilder =
    http("Create a Misc case")
      .post(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Misc Description")
      .formParam("contactName", "Contact Name")
      .formParam("caseType", "IB")
      .check(status.is(SEE_OTHER.code()))

  def getNewMiscCaseViaAdvancedSearch: HttpRequestBuilder =
    http("GET Find a Valid Misc Case Reference")
      .get(s"$operatorUiBaseUrl/search?case_details=&commodity_code=&case_source=Contact+Name&decision_details=&keyword%5B0%5D=" +
        s"&addToSearch=true&application_type%5B3%5D=MISCELLANEOUS&status%5B4%5D=NEW&selectedTab=details#advanced_search-results_and_filters"
      )
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
      .check(css("#advanced_search_results-row-0-reference-link").find.saveAs("miscCaseReference"))

  def getMiscChooseReleaseTeam: HttpRequestBuilder =
    http("GET Release Misc Case to a team")
      .get(operatorUiBaseUrl + "/cases/${miscCaseReference}/release")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postMiscChooseReleaseTeam: HttpRequestBuilder =
    http("POST Release to a team")
      .post(operatorUiBaseUrl + "/cases/${miscCaseReference}/release")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "flex")
      .check(status.is(SEE_OTHER.code()))

  def getMiscCaseReleasedConfirmation: HttpRequestBuilder =
    http("GET Case release confirmation")
      .get(operatorUiBaseUrl + "/cases/${miscCaseReference}/release/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
}
