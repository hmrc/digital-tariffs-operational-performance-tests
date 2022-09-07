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

object TraderUiRequests extends DigitalTariffsPerformanceTestRunner {

  private val homePage = s"$traderUiBaseUrl/applications-and-rulings"

  def getTraderStartPage: HttpRequestBuilder =
    http("Get start")
      .get(traderUiBaseUrl)
      .check(status.is(OK.code()))
      .check(header("Location").is(homePage))

  def getInformationYouNeed: HttpRequestBuilder =
    http("Information you need to apply for a ruling")
      .get(s"$traderUiBaseUrl/information-you-need")
      .check(status.is(OK.code()))

  def getInformationMadePublic: HttpRequestBuilder =
    http("Some of the information you provide")
      .get(s"$traderUiBaseUrl/information-may-be-made-public")
      .check(status.is(OK.code()))

  def getGoodsName: HttpRequestBuilder =
    http("Provide a name for the goods")
      .get(s"$traderUiBaseUrl/provide-goods-name")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postGoodsName: HttpRequestBuilder =
    http("Provide a name for the goods")
      .post(s"$traderUiBaseUrl/provide-goods-name")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("goodsName", "Snow man jacket")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postGoodsDescription: HttpRequestBuilder =
    http("Provide a detailed description")
      .post(s"$traderUiBaseUrl/provide-goods-description")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("goodsDescription", "Snow man jacket in black colour")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postConfidentialInfo: HttpRequestBuilder =
    http("Do you want to add any confidential information")
      .post(s"$traderUiBaseUrl/add-confidential-information")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postUploadSupportingDocument: HttpRequestBuilder =
    http("Do you want to upload any supporting documents?")
      .post(s"$traderUiBaseUrl/add-supporting-documents")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false") // for simplicity we do not send files in Jenkins
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAreYouSendingASample: HttpRequestBuilder =
    http("Are you sending a sample")
      .post(s"$traderUiBaseUrl/are-you-sending-samples")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postHaveYouFoundCommodityCode: HttpRequestBuilder =
    http("Have you found a commodity code")
      .post(s"$traderUiBaseUrl/have-you-found-commodity-code")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postLegalChallenge: HttpRequestBuilder =
    http("Have there been any legal challenges")
      .post(s"$traderUiBaseUrl/any-legal-challenges-classifying-goods")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postPreviousRulingReference: HttpRequestBuilder =
    http("Do you have a previous ruling reference")
      .post(s"$traderUiBaseUrl/previous-ruling-reference")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postSimilarRuling: HttpRequestBuilder =
    http("Are there similar goods")
      .post(s"$traderUiBaseUrl/ruling-on-similar-goods")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postRegisterForEori: HttpRequestBuilder =
    http("Registered Address For Eori")
      .post(s"$traderUiBaseUrl/provide-registered-eori-details")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("eori", s"$eoriNumber")
      .formParam("businessName", "Digital Tariffs Limited Company")
      .formParam("addressLine1", "Victoria Road 10")
      .formParam("townOrCity", "Shipley")
      .formParam("postcode", "LS10 6HT")
      .formParam("country", "GB")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postEnterContactDetails: HttpRequestBuilder =
    http("Provide the contact details")
      .post(s"$traderUiBaseUrl/provide-contact-details")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Joe Bloggs")
      .formParam("email", "joe.bloggs@example.sh")
      .formParam("phoneNumber", "0123456789")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postCheckYourAnswers: HttpRequestBuilder =
    http("Check Your Answers")
      .post(s"$traderUiBaseUrl/check-your-answers")
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(OK.code()))
}
