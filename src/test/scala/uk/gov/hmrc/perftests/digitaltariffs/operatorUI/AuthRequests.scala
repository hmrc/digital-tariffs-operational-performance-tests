/*
 * Copyright 2026 HM Revenue & Customs
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
import io.gatling.core.session.StaticValueExpression

object AuthRequests extends DigitalTariffsPerformanceTestRunner {

  def getAuthLoginStub: HttpRequestBuilder =
    http("GET Auth Login Stub Sign In")
      .get(s"$authStubBaseUrl/gg-sign-in")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAuthLoginStub: HttpRequestBuilder =
    http("POST Auth Login Stub Sign In")
      .post(s"$authStubBaseUrl/gg-sign-in")
      .formParam("csrfToken", session => session("csrfToken").as[String])
      .formParam("authorityId", StaticValueExpression(""))
      .formParam("gatewayToken", StaticValueExpression(""))
      .formParam("redirectionUrl", StaticValueExpression(s"$traderUiBaseUrl/applications-and-rulings"))
      .formParam("credentialStrength", StaticValueExpression("strong"))
      .formParam("confidenceLevel", StaticValueExpression("50"))
      .formParam("affinityGroup", StaticValueExpression("Individual"))
      .formParam("email", StaticValueExpression("user@test.com"))
      .formParam("credentialRole", StaticValueExpression("User"))
      .formParam("additionalInfo.emailVerified", StaticValueExpression("N/A"))
      .formParam("enrolment[0].name", StaticValueExpression("HMRC-ATAR-ORG"))
      .formParam("enrolment[0].taxIdentifier[0].name", StaticValueExpression("EORINumber"))
      .formParam("enrolment[0].taxIdentifier[0].value", StaticValueExpression(eoriNumber))
      .formParam("enrolment[0].state", StaticValueExpression("Activated"))
      .check(status.is(SEE_OTHER.code()))
}
