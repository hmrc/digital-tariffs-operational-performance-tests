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

package uk.gov.hmrc.perftests.digitaltariffs.operatorUI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import io.netty.handler.codec.http.HttpResponseStatus._
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object DeleteAllCasesRequests extends DigitalTariffsPerformanceTestRunner with RequestUtils {

  def deleteAllCases(): HttpRequestBuilder =
    http("DELETE all cases")
      .delete(s"$bindingTariffClassificationBackendUrl/cases")
      .headers(
        Map(
          "X-Api-Token"  -> "9253947-99f3-47d7-9af2-b75b4f37fd34",
          "Content-Type" -> "application/json"
        )
      )
      .check(status.is(NO_CONTENT.code()))

}