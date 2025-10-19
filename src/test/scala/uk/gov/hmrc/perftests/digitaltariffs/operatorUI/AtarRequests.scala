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

object AtarRequests extends DigitalTariffsPerformanceTestRunner with RequestUtils {

  private val homePage = s"$operatorUiBaseUrl/operator-dashboard-classification"

  def getStartPage: HttpRequestBuilder =
    http("GET Home Page")
      .get(homePage)
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
      .check(currentLocation.is(homePage))

  def getGatewayQueue: HttpRequestBuilder =
    http("Gateway Queue")
      .get(s"$operatorUiBaseUrl/gateway-cases")
      .disableFollowRedirect
      .check(status.is(OK.code()))

  def getCaseTraderDetails: HttpRequestBuilder =
    http("GET View Case Trader Details")
      .get(operatorUiBaseUrl + "/cases/v2/#{atarCaseReference}/atar")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getReleaseOrSuppressAtarCase: HttpRequestBuilder =
    http("GET Action Atar release or suppress case")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/release-or-suppress-case")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postReleaseOrSuppressAtarCase: HttpRequestBuilder =
    http("POST Action Atar release or suppress case")
      .post(operatorUiBaseUrl + "/cases/#{atarCaseReference}/release-or-suppress-case")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("caseStatus", "release")
      .check(status.is(SEE_OTHER.code()))

  def getAtarReleaseToAQueue: HttpRequestBuilder =
    http("GET Choose a team to release")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/release")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAtarReleaseToAQueue: HttpRequestBuilder =
    http("POST Choose a team to release")
      .post(operatorUiBaseUrl + "/cases/#{atarCaseReference}/release")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("queue", "act")
      .check(status.is(SEE_OTHER.code()))

  def getAtarReleaseConfirmation: HttpRequestBuilder =
    http("GET Release confirmation")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/release/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getAtarOpenCases: HttpRequestBuilder =
    http("GET ATaR Open cases")
      .get(operatorUiBaseUrl + "/all-open-cases")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getAtarAssignCase: HttpRequestBuilder =
    http("GET Assign a case")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/assign")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAtarAssignCase: HttpRequestBuilder =
    http("POST Assign a case")
      .post(operatorUiBaseUrl + "/cases/#{atarCaseReference}/assign")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("state", "true")
      .check(status.is(SEE_OTHER.code()))

  def getAtarChangeCaseStatusRefer: HttpRequestBuilder =
    http("GET Change a case status")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/change-case-status")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAtarChangeCaseStatusRefer: HttpRequestBuilder =
    http("POST Change a case status")
      .post(operatorUiBaseUrl + "/cases/#{atarCaseReference}/change-case-status")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("caseStatus", "refer")
      .check(status.is(SEE_OTHER.code()))

  def getAtarReferCase: HttpRequestBuilder =
    http("GET Refer a case")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/refer-reason")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAtarReferCase: HttpRequestBuilder =
    http("POST Refer a case")
      .post(operatorUiBaseUrl + "/cases/#{atarCaseReference}/refer-reason")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("referredTo", "Laboratory analyst")
      .formParam("note", "A note from me")
      .check(status.is(SEE_OTHER.code()))

  def getAtarFileUpload: HttpRequestBuilder =
    http("GET Upload file refer case")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/refer-email")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
      .check(saveFileUploadurl)
      .check(saveCallBack)
      .check(saveAmazonDate)
      .check(saveAmazonCredential)
      .check(saveUpscanIniateResponse)
      .check(saveUpscanInitiateRecieved)
      .check(saveAmazonMetaOriginalFileName)
      .check(saveAmazonAlgorithm)
      .check(saveKey)
      .check(saveAMZMetaRequestId)
      .check(saveAMZMetaSessionId)
      .check(saveAmazonSignature)
      .check(savePolicy)
      .check(saveSuccessRedirect)
      .check(saveErrorRedirect)

  def postAtarFileUpload: HttpRequestBuilder = {
    val file = System.getProperty("user.dir") + "/src/test/files/FileUploadPDF.pdf"
    http("POST Upload file refer case")
      .post("https://www.staging.upscan.tax.service.gov.uk/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryA81LRm2SGmby4vFN")
      .asMultipartForm
      .bodyPart(StringBodyPart("success_action_redirect", "#{successRedirect}"))
      .bodyPart(StringBodyPart("error_action_redirect", "#{errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "#{callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "#{amazonDate}"))
      .bodyPart(StringBodyPart("x-amz-credential", "#{amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "#{upscanInitiateResponse}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "#{upscanInitiateReceived}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "#{amazonMetaRequestID}"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "#{amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "#{key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "#{amazonSignature}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "#{amazonMetaSessionID}"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "binding-tariff-filestore"))
      .bodyPart(StringBodyPart("policy", "#{policy}"))
      .bodyPart(RawFileBodyPart("file", file))
      .check(status.is(SEE_OTHER.code()))
  }

  def getAtarReferConfirmation: HttpRequestBuilder =
    http("GET Case referred confirmation")
      .get(operatorUiBaseUrl + "/cases/#{atarCaseReference}/refer/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
}
