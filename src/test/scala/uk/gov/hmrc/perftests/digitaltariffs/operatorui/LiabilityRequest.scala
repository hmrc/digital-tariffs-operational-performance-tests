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

object LiabilityRequest extends DigitalTariffsPerformanceTestRunner with RequestUtils {

  val extractNumbers: String => String = { (s: String) =>
    """\d+""".r.findFirstIn(s).getOrElse("No Case No. found")
  }

  def getOpenLiability: HttpRequestBuilder =
    http("GET Open Liability")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_liability_tab")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getNewLiability: HttpRequestBuilder =
    http("GET New liability information")
      .get(s"$operatorUiBaseUrl/new-liability")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postNewLiability: HttpRequestBuilder =
    http("POST Post-New liability information")
      .post(s"$operatorUiBaseUrl/new-liability")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("item-name", "Unique Performance Test Liability")
      .formParam("trader-name", "Unique PT Trader Joe")
      .formParam("liability-status", "LIVE")
      .check(status.is(SEE_OTHER.code()))
      .check(header("location").transform(extractNumbers).saveAs("liabilityCaseReference"))

  //  def getLiabilityRef: HttpRequestBuilder =
  //    http("GET Find Valid Case Reference")
  //      .get(
  //        s"$operatorUiBaseUrl/search?case_source=.*&commodity_code=&decision_details=&keyword%5B0%5D=" +
  //          s"&addToSearch=false&application_type%5B1%5D=LIABILITY_ORDER&status%5B4%5D=NEW" +
  //          s"&selectedTab=details#advanced_search-results_and_filters"
  //      )
  //      .check(status.is(OK.code()))
  //      .check(saveCsrfToken)
  //      .check(css("#advanced_search_results-row-0-reference-link").find.saveAs("case_reference"))

  def getLiabilityCase: HttpRequestBuilder =
    http("GET Liability page")
      .get(operatorUiBaseUrl + s"/cases/v2/$${liabilityCaseReference}/liability")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getActionThisCase: HttpRequestBuilder =
    http("GET Action liability case")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/release-or-suppress-case")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postActionThisCase: HttpRequestBuilder =
    http("POST Action liability case")
      .post(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/release-or-suppress-case")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("caseStatus", "release")
      .check(status.is(SEE_OTHER.code()))

  def getReleaseToAQueue: HttpRequestBuilder =
    http("GET Choose a team to release")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/release")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postReleaseToAQueue: HttpRequestBuilder =
    http("POST Choose a team to release")
      .post(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/release")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "act")
      .check(status.is(SEE_OTHER.code()))

  def getReleaseConfirmation: HttpRequestBuilder =
    http("GET Release confirmation")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/release/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getOpenCases: HttpRequestBuilder =
    http("GET ATaR Open cases")
      .get(operatorUiBaseUrl + "/all-open-cases")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def getAssignCase: HttpRequestBuilder =
    http("GET Assign a case")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/assign")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postAssignCase: HttpRequestBuilder =
    http("POST Assign a case")
      .post(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/assign")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("state", "true")
      .check(status.is(SEE_OTHER.code()))

  def getChangeCaseStatusRefer: HttpRequestBuilder =
    http("GET Change a case status")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/change-case-status")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postChangeCaseStatusRefer: HttpRequestBuilder =
    http("POST Change a case status")
      .post(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/change-case-status")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("caseStatus", "refer")
      .check(status.is(SEE_OTHER.code()))

  def getReferCase: HttpRequestBuilder =
    http("GET Refer a case")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/refer-reason")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)

  def postReferCase: HttpRequestBuilder =
    http("POST Refer a case")
      .post(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/refer-reason")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("referredTo", "Laboratory analyst")
      .formParam("note", "A note from me")
      .check(status.is(SEE_OTHER.code()))

  def getFileUpload: HttpRequestBuilder = {
    http("GET Upload file refer case")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/refer-email")
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
  }

  def postFileUpload: HttpRequestBuilder = {
    val file = System.getProperty("user.dir") + "/src/test/files/FileUploadPDF.pdf"
    http("POST Upload file refer case")
      .post("https://www.staging.upscan.tax.service.gov.uk/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryA81LRm2SGmby4vFN")
      .asMultipartForm
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "${amazonMetaRequestID}"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "${amazonMetaSessionID}"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "binding-tariff-filestore"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(RawFileBodyPart("file", file))
      //      .bodyPart(
      //        RawFileBodyPart("file", file)
      //          .contentType("application/pdf")
      //          .fileName("FileUploadPDF.pdf")
      //      )
      .check(status.is(SEE_OTHER.code()))
  }

  def getReferConfirmation: HttpRequestBuilder =
    http("GET Case referred confirmation")
      .get(operatorUiBaseUrl + s"/cases/$${liabilityCaseReference}/refer/confirmation")
      .check(status.is(OK.code()))
      .check(saveCsrfToken)
}
