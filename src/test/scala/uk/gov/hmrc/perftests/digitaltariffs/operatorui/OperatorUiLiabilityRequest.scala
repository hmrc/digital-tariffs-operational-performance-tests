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
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiLiabilityRequest extends DigitalTariffsPerformanceTestRunner {

  def getOpenLiability: HttpRequestBuilder =
    http("Open Liability")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_liability_tab")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getNewLiability: HttpRequestBuilder =
    http("Get-New liability information")
      .get(s"$operatorUiBaseUrl/new-liability")
      .check(status.is(HttpResponseStatus.OK.code()))
      .check(saveCsrfToken)

  def postNewLiability: HttpRequestBuilder =
    http("Post-New liability information")
      .post(s"$operatorUiBaseUrl/new-liability")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("item-name", "Unique Performance Test Liability")
      .formParam("trader-name", "Unique PT Trader Joe")
      .formParam("liability-status", "LIVE")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getLiabilityRef: HttpRequestBuilder =
    http("Find Valid Case Reference")
      .get(
        s"$operatorUiBaseUrl/search?case_source=.*&commodity_code=&decision_details=&keyword%5B0%5D=" +
          s"&addToSearch=false&application_type%5B1%5D=LIABILITY_ORDER&status%5B4%5D=NEW" +
          s"&selectedTab=details#advanced_search-results_and_filters"
      )
      .check(status.is(HttpResponseStatus.OK.code()))
      .check(css("a#advanced_search_results-row-0-reference").find.saveAs("case_reference"))

  def getCaseToAction: HttpRequestBuilder =
    http("Liability case reference ${case_reference}")
      .get(operatorUiBaseUrl + "/cases/v2/${case_reference}/liability")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getActionCase: HttpRequestBuilder =
    http("Action liability case ${case_reference}")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/release-or-suppress-case")
      .formParam("caseStatus", "release")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getReleaseToAQueue: HttpRequestBuilder =
    http("Choose a team to release")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/release")
      .formParam("queue", "ACT")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getReleaseConfirmation: HttpRequestBuilder =
    http("Release confirmation")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/release/confirmation")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getOpenCases: HttpRequestBuilder =
    http("ATaR Open cases")
      .get(operatorUiBaseUrl + "/all-open-cases")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getAssignCase: HttpRequestBuilder =
    http("Assign a case")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/assign")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getChangeCaseStatusRefer: HttpRequestBuilder =
    http("Change a case status")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/change-case-status")
      .formParam("caseStatus", "refer")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getReferCase: HttpRequestBuilder =
    http("Refer a case")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/refer-reason")
      .formParam("referredTo", "Laboratory analyst")
      .formParam("note", "A note from me")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getFileUpload: HttpRequestBuilder = {
    val file = System.getProperty("user.dir") + "/src/test/files/FileUploadPDF.pdf"
    http("Post Select file as success")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/refer-email")
      .formUpload("fileToUpload", file)
      .formParam("file", "Upload")
      .check(status.is(HttpResponseStatus.OK.code()))
  }

  def getReferConfirmation: HttpRequestBuilder =
    http("Case referred")
      .get(operatorUiBaseUrl + "/cases/${case_reference}/refer/confirmation")
      .check(status.is(HttpResponseStatus.OK.code()))
}
