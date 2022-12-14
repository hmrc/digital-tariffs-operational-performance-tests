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

object TraderUiRequests extends DigitalTariffsPerformanceTestRunner with RequestUtils {

  def getYourApplicationsAndRulingsPage: HttpRequestBuilder =
    http("GET Your applications and rulings page")
      .get(traderUiBaseUrl)
      .check(status.is(SEE_OTHER.code()))

  def getInformationYouNeed: HttpRequestBuilder =
    http("GET Information you need to apply for a ruling")
      .get(s"$traderUiBaseUrl/information-you-need")
      .check(status.is(OK.code()))

  def getInformationMadePublic: HttpRequestBuilder =
    http("GET Information provided may appear public")
      .get(s"$traderUiBaseUrl/information-may-be-made-public")
      .check(status.is(OK.code()))

  def getGoodsName: HttpRequestBuilder =
    http("GET Provide a name for the goods")
      .get(s"$traderUiBaseUrl/provide-goods-name")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postGoodsName: HttpRequestBuilder =
    http("POST Provide a name for the goods page")
      .post(s"$traderUiBaseUrl/provide-goods-name")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("goodsName", "Snow man jacket")
      .check(status.is(SEE_OTHER.code()))

  def getGoodsDescription: HttpRequestBuilder =
    http("GET Provide a detailed description page")
      .get(s"$traderUiBaseUrl/provide-goods-description")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postGoodsDescription: HttpRequestBuilder =
    http("POST Provide a detailed description page")
      .post(s"$traderUiBaseUrl/provide-goods-description")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("goodsDescription", "Snow man jacket in black colour")
      .check(status.is(SEE_OTHER.code()))

  def getConfidentialInfo: HttpRequestBuilder =
    http("GET Add confidential information page")
      .get(s"$traderUiBaseUrl/add-confidential-information")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postConfidentialInfo: HttpRequestBuilder =
    http("POST Add confidential information page")
      .post(s"$traderUiBaseUrl/add-confidential-information")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getUploadSupportingDocument: HttpRequestBuilder =
    http("GET Do you want to upload any supporting documents?")
      .get(s"$traderUiBaseUrl/add-supporting-documents")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postUploadSupportingDocument: HttpRequestBuilder =
    http("POST Do you want to upload any supporting documents?")
      .post(s"$traderUiBaseUrl/add-supporting-documents")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false") // for simplicity we do not send files in Jenkins
      .check(status.is(SEE_OTHER.code()))

  def getAreYouSendingASample: HttpRequestBuilder =
    http("GET Are you sending a sample page")
      .get(s"$traderUiBaseUrl/are-you-sending-samples")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAreYouSendingASample: HttpRequestBuilder =
    http("POST Are you sending a sample page")
      .post(s"$traderUiBaseUrl/are-you-sending-samples")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getHaveYouFoundCommodityCode: HttpRequestBuilder =
    http("GET Have you found a commodity code page")
      .get(s"$traderUiBaseUrl/have-you-found-commodity-code")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postHaveYouFoundCommodityCode: HttpRequestBuilder =
    http("Have you found a commodity code")
      .post(s"$traderUiBaseUrl/have-you-found-commodity-code")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getLegalChallenge: HttpRequestBuilder =
    http("GET Legal challenges page")
      .get(s"$traderUiBaseUrl/any-legal-challenges-classifying-goods")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postLegalChallenge: HttpRequestBuilder =
    http("POST Legal challenges page")
      .post(s"$traderUiBaseUrl/any-legal-challenges-classifying-goods")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getPreviousRulingReference: HttpRequestBuilder =
    http("GET Previous ruling reference page")
      .get(s"$traderUiBaseUrl/previous-ruling-reference")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postPreviousRulingReference: HttpRequestBuilder =
    http("POST Previous ruling reference page")
      .post(s"$traderUiBaseUrl/previous-ruling-reference")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getSimilarRuling: HttpRequestBuilder =
    http("GET Similar goods page")
      .get(s"$traderUiBaseUrl/ruling-on-similar-goods")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postSimilarRuling: HttpRequestBuilder =
    http("POST Similar goods page")
      .post(s"$traderUiBaseUrl/ruling-on-similar-goods")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("value", "false")
      .check(status.is(SEE_OTHER.code()))

  def getRegisterForEori: HttpRequestBuilder =
    http("GET Registered Address For Eori page")
      .get(s"$traderUiBaseUrl/provide-registered-eori-details")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postRegisterForEori: HttpRequestBuilder =
    http("POST Registered Address For Eori")
      .post(s"$traderUiBaseUrl/provide-registered-eori-details")
      .formParamSeq(
        Seq(
          "csrfToken"    -> s"$${csrfToken}",
          "eori"         -> s"$eoriNumber",
          "businessName" -> "Digital Tariffs Limited Company",
          "addressLine1" -> "Victoria Road 10",
          "townOrCity"   -> "Shipley",
          "postcode"     -> "LS10 6HT",
          "country"      -> "GB"
        )
      )
      .check(status.is(SEE_OTHER.code()))

  def getEnterContactDetails: HttpRequestBuilder =
    http("GET Provide the contact details")
      .get(s"$traderUiBaseUrl/provide-contact-details")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postEnterContactDetails: HttpRequestBuilder =
    http("POST Provide the contact details")
      .post(s"$traderUiBaseUrl/provide-contact-details")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Joe Bloggs")
      .formParam("email", "joe.bloggs@example.sh")
      .formParam("phoneNumber", "0123456789")
      .check(status.is(SEE_OTHER.code()))

  def getCheckYourAnswers: HttpRequestBuilder =
    http("GET Check Your Answers")
      .get(s"$traderUiBaseUrl/check-your-answers")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postCheckYourAnswers: HttpRequestBuilder =
    http("POST Check Your Answers")
      .post(s"$traderUiBaseUrl/check-your-answers")
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(SEE_OTHER.code()))

  def getConfirmationPage: HttpRequestBuilder =
    http("GET Confirmation page")
      .get(s"$traderUiBaseUrl/application-complete")
      .check(status.is(OK.code()))
      .check(css("#confirmation-reference").find.transform(extractNumbers).saveAs("atarCaseReference"))
}
