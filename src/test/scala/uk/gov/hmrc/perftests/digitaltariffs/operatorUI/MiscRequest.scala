/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.digitaltariffs.operatorUI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import io.netty.handler.codec.http.HttpResponseStatus._
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object MiscRequest extends DigitalTariffsPerformanceTestRunner with RequestUtils {

  def getMiscCase: HttpRequestBuilder =
    http("GET Misc tab")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_miscellaneous_tab")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getCreateMiscCase: HttpRequestBuilder =
    http("GET Create Misc page")
      .get(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postCreateNewMiscCase: HttpRequestBuilder =
    http("POST Create a Misc case")
      .post(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Misc Description")
      .formParam("contactName", "Contact Name")
      .formParam("caseType", "IB")
      .check(status.is(SEE_OTHER.code()))
      .check(header("location").transform(extractNumbers).saveAs("miscCaseReference"))

  def getMiscChooseReleaseTeam: HttpRequestBuilder =
    http("GET Release Misc Case to a team")
      .get(operatorUiBaseUrl + s"/cases/$${miscCaseReference}/release")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postMiscChooseReleaseTeam: HttpRequestBuilder =
    http("POST Release to a team")
      .post(operatorUiBaseUrl + s"/cases/$${miscCaseReference}/release")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "flex")
      .check(status.is(SEE_OTHER.code()))

  def getMiscCaseReleasedConfirmation: HttpRequestBuilder =
    http("GET Case release confirmation")
      .get(operatorUiBaseUrl + s"/cases/$${miscCaseReference}/release/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
}
