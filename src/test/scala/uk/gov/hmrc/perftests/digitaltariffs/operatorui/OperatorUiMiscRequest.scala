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
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiMiscRequest extends DigitalTariffsPerformanceTestRunner {

  def getMiscCase: HttpRequestBuilder = {
    http("Get to Misc")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_miscellaneous_tab")
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def postCreateMisc: HttpRequestBuilder = {
    http("Create Correspondence")
      .post(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Misc Description")
      .formParam("contactName", "Contact Name")
      .formParam("caseType", "IB")
      .check(status.is(200))
      .check(regex("Miscellaneous case (8[0-9]{8})").saveAs("caseRef"))
  }
}


