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
import io.netty.handler.codec.http.HttpResponseStatus._
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object CorrespondenceRequest extends DigitalTariffsPerformanceTestRunner {

  val extractNumbers: String => String = { (s: String) =>
    """\d+""".r.findFirstIn(s).getOrElse("Not found")
  }

  val getCaseReference =
    css("#case-reference")
      .find
      .transform(extractNumbers)
      .saveAs("correspondenceCaseReference")

  def getCorrespondenceCase: HttpRequestBuilder =
    http("GET Correspondence")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_correspondence_tab")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getCreateNewCorrespondenceCase: HttpRequestBuilder =
    http("GET Create A NEW Correspondence")
      .get(s"$operatorUiBaseUrl/new-correspondence")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postCreateNewCorrespondenceCase: HttpRequestBuilder =
    http("POST Create A NEW Correspondence")
      .post(s"$operatorUiBaseUrl/new-correspondence")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("summary", "Case Description")
      .formParam("source", "a trader")
      .formParam("contactEmail", "abv@abv.com")
      .check(status.is(SEE_OTHER.code()))
      .check(header("location").saveAs("caseRefUrl"))

  //  def getCorrespondenceRefViaAdvancedSearch: HttpRequestBuilder =
  //    http("GET Find a Valid Correspondence Case Reference")
  //      .get(
  //        s"$operatorUiBaseUrl/search?case_details=&commodity_code=&case_source=a+trader&decision_details=&keyword%5B0%5D=" +
  //          s"&addToSearch=false&application_type%5B2%5D=CORRESPONDENCE&status%5B4%5D=NEW&selectedTab=details#advanced_search-results_and_filters"
  //      )
  //      .check(status.is(OK.code()))
  //      .check(saveCsrfToken)
  //      .check(css("#advanced_search_results-row-0-reference-link").find.saveAs("correspondenceCaseReference"))

  def getReleaseCorrespondenceCase: HttpRequestBuilder =
    http("GET Release NEW Correspondence Case")
      //      .get(operatorUiBaseUrl + "/release-correspondence-choice?reference=$${correspondenceCaseReference}")
      .get(s"${baseUrlFor("tariff-classification-frontend")}/$${caseRefUrl}")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
      .check(getCaseReference)

  def postReleaseCorrespondenceCase: HttpRequestBuilder =
    http("POST Release NEW Correspondence Case")
      .post(operatorUiBaseUrl + s"/release-correspondence-choice?reference=$${correspondenceCaseReference}")
      //      .post(s"${baseUrlFor("tariff-classification-frontend")}/$${caseRefUrl}")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("choice", "Yes")
      .check(status.is(SEE_OTHER.code()))
  //      .check(header("location").saveAs("chooseReleaseTeamUrl"))

  def getChooseReleaseTeam: HttpRequestBuilder =
    http("GET Release to a team")
      .get(operatorUiBaseUrl + s"/cases/$${correspondenceCaseReference}/release")
      //      .get(s"${baseUrlFor("tariff-classification-frontend")}/$${chooseReleaseTeamUrl}")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postChooseReleaseTeam: HttpRequestBuilder =
    http("POST Release to a team")
      .post(operatorUiBaseUrl + s"/cases/$${correspondenceCaseReference}/release")
      //      .post(s"${baseUrlFor("tariff-classification-frontend")}/$${chooseReleaseTeamUrl}")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "flex")
      .check(status.is(SEE_OTHER.code()))
      .check(header("location").saveAs("relativeCaseReleasedConfirmationUrl"))

  def getCaseReleasedConfirmation: HttpRequestBuilder =
    http("GET Case release confirmation")
      .get(operatorUiBaseUrl + s"/cases/$${correspondenceCaseReference}/release/confirmation")
      //      .get(s"${baseUrlFor("tariff-classification-frontend")}/$${relativeCaseReleasedConfirmationUrl}")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

}
