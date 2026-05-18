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

object StrideAuthRequests extends DigitalTariffsPerformanceTestRunner {

  val baseUrl: String             = baseUrlFor("tariff-classification-frontend")
  private val jsRelayStatePattern = """<input type="hidden" id="RelayState" name="RelayState" value="([^"]+)""""
  private val relayStatePattern   =
    """<input class="govuk-input" type="hidden" id="RelayState" name="RelayState" value="([^"]+)""""
  private val samlResponsePattern = """<input type="hidden" name="SAMLResponse" value="([^"]+)""""

  private val formUrlPattern   = """action="([^"]+)""""
  private val strideIdpBaseUrl = baseUrlFor("stride-idp-stub")

  private def savePageItem(name: String, pattern: String) =
    regex(_ => pattern).saveAs(name)

  val getProtectedPageNoSession: HttpRequestBuilder =
    http("GET Stride Auth page without session")
      .get(operatorUiBaseUrl)
      .disableFollowRedirect
      .check(status.is(SEE_OTHER.code()))
      .check(header(StaticValueExpression("location")).saveAs("protectedPageRedirect"))

  val getStrideSignIn: HttpRequestBuilder =
    if (runLocal) {
      http("GET redirect to STRIDE Auth")
        .get("#{protectedPageRedirect}")
        .disableFollowRedirect
        .check(status.is(SEE_OTHER.code()))
        .check(header(StaticValueExpression("location")).saveAs("authRequestRedirect"))
    } else {
      http("GET redirect to STRIDE Auth")
        .get(baseUrlFor("tariff-classification-frontend") + "#{protectedPageRedirect}")
        .disableFollowRedirect
        .check(status.is(SEE_OTHER.code()))
        .check(header(StaticValueExpression("location")).saveAs("authRequestRedirect"))
    }

  val getIdpSignInPage: HttpRequestBuilder =
    http("GET Stride Auth redirect to IdP login")
      .get("#{authRequestRedirect}")
      .check(status.is(OK.code()))
      .check(savePageItem("relayState", relayStatePattern))
      .check(savePageItem("formUrl", formUrlPattern))

  val postIdpSignInPage: HttpRequestBuilder =
    http("POST Stride Auth IdP login form")
      .post(s"$strideIdpBaseUrl#{formUrl}")
      .disableFollowRedirect
      .formParam("RelayState", session => session("relayState").as[String])
      .formParam("pid", StaticValueExpression("12345"))
      .formParam("status", true)
      .formParam("signature", StaticValueExpression("valid"))
      .formParam("roles", StaticValueExpression("classification"))
      .check(status.is(SEE_OTHER.code()))
      .check(header(StaticValueExpression("location")).saveAs("signInRedirect"))

  val getSignInRedirect: HttpRequestBuilder =
    http("GET page w/ JS redirect to STRIDE Auth")
      .get(s"$strideIdpBaseUrl#{signInRedirect}")
      .check(status.is(OK.code()))
      .check(savePageItem("formUrl", formUrlPattern))
      .check(savePageItem("samlResponse", samlResponsePattern))
      .check(savePageItem("relayState", jsRelayStatePattern))

  val postIdpResponseToStride: HttpRequestBuilder =
    http("POST IdP response to STRIDE Auth")
      .post("#{formUrl}")
      .disableFollowRedirect
      .formParam("SAMLResponse", session => session("samlResponse").as[String])
      .formParam("RelayState", session => session("relayState").as[String])
      .check(status.is(SEE_OTHER.code()))
      .check(header(StaticValueExpression("location")).saveAs("loginRedirect"))
}
