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

object OperatorUiCorrespondenceRequest extends DigitalTariffsPerformanceTestRunner {

  def getCorrespondenceCase: HttpRequestBuilder =
    http("Get to Correspondence")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_correspondence_tab")
      .check(status.is(HttpResponseStatus.OK.code()))
      .check(saveCsrfToken)

  def postCreateCorrespondence: HttpRequestBuilder =
    http("Create Correspondence")
      .post(s"$operatorUiBaseUrl/new-correspondence")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("summary", "Case Description")
      .formParam("source", "a trader")
      .formParam("contactEmail", "abv@abv.com")
      .check(status.is(HttpResponseStatus.OK.code()))
      .check(regex("Correspondence case (8[0-9]{8})").saveAs("caseRef"))

  def postReleaseCorrespondenceCase: HttpRequestBuilder =
    http("Case released")
      .post(operatorUiBaseUrl + "/release-correspondence-choice?reference=${caseRef}")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("choice", "Yes")
      .check(status.is(HttpResponseStatus.OK.code()))

  def postChooseReleaseTeam: HttpRequestBuilder =
    http("Release to a team")
      .post(operatorUiBaseUrl + "/cases/${caseRef}/release")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "flex")
      .check(status.is(HttpResponseStatus.OK.code()))

  def getCaseReleasedConfirmation: HttpRequestBuilder =
    http("Case release confirmation")
      .get(operatorUiBaseUrl + "/cases/${caseRef}/release/confirmation")
      .check(status.is(HttpResponseStatus.OK.code()))
}
