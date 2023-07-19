/*
 * Copyright 2023 HM Revenue & Customs
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
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType

trait RequestUtils {

  val extractNumbers: String => String = { (s: String) =>
    """\d+""".r.findFirstIn(s).getOrElse("No Case Ref found")
  }

  private val amazonUrlPattern        = """action="(.*?)""""
  private val callBackUrPattern       = """name="x-amz-meta-callback-url" value="(.*?)""""
  private val amzDatePattern          = """name="x-amz-date" value="(.*?)""""
  private val credentialPattern       = """name="x-amz-credential" value="(.*?)""""
  private val initiateResponsePattern = """name="x-amz-meta-upscan-initiate-response" value="(.*?)""""
  private val initiateReceivedPattern = """name="x-amz-meta-upscan-initiate-received" value="(.*?)""""
  private val metaOriginalFilename    = """name="x-amz-meta-original-filename" value="(.*?)""""
  private val algorithmPattern        = """name="x-amz-algorithm" value="(.*?)""""
  private val keyPattern              = """name="key" value="(.*?)""""
  private val signaturePattern        = """name="x-amz-signature" value="(.*?)""""
  private val policyPattern           = """name="policy" value="(.*?)""""
  private val referencePattern        = """data-reference="(.*?)""""
  private val fileTypePattern         = """data-filetype="(.*?)""""
  private val successRedirectPattern  = """name="success_action_redirect" value="(.*?)""""
  private val errorRedirectPattern    = """name="error_action_redirect" value="(.*?)""""
  private val metaRequestIDPattern    = """name="x-amz-meta-request-id" value="(.*?)""""
  private val metaSesssionIDPattern   = """name="x-amz-meta-session-id" value="(.*?)""""

  def saveSuccessRedirect: CheckBuilder[RegexCheckType, String, String] =
    regex(successRedirectPattern).saveAs("successRedirect")

  def saveErrorRedirect: CheckBuilder[RegexCheckType, String, String] =
    regex(errorRedirectPattern).saveAs("errorRedirect")

  def saveFileUploadurl: CheckBuilder[RegexCheckType, String, String] =
    regex(amazonUrlPattern).saveAs("fileUploadAmazonUrl")

  def saveCallBack: CheckBuilder[RegexCheckType, String, String] =
    regex(callBackUrPattern).saveAs("callBack")

  def saveReference: CheckBuilder[RegexCheckType, String, String] =
    regex(referencePattern).saveAs("reference")

  def saveFileType: CheckBuilder[RegexCheckType, String, String] =
    regex(fileTypePattern).saveAs("fileType")

  def saveAmazonDate: CheckBuilder[RegexCheckType, String, String] =
    regex(amzDatePattern).saveAs("amazonDate")

  def saveAmazonCredential: CheckBuilder[RegexCheckType, String, String] =
    regex(credentialPattern).saveAs("amazonCredential")

  def saveUpscanIniateResponse: CheckBuilder[RegexCheckType, String, String] =
    regex(initiateResponsePattern).saveAs("upscanInitiateResponse")

  def saveUpscanInitiateRecieved: CheckBuilder[RegexCheckType, String, String] =
    regex(initiateReceivedPattern).saveAs("upscanInitiateReceived")

  def saveAmazonMetaOriginalFileName: CheckBuilder[RegexCheckType, String, String] =
    regex(metaOriginalFilename).saveAs("amazonMetaOriginalFileName")

  def saveAmazonAlgorithm: CheckBuilder[RegexCheckType, String, String] =
    regex(algorithmPattern).saveAs("amazonAlgorithm")

  def saveKey: CheckBuilder[RegexCheckType, String, String] =
    regex(keyPattern).saveAs("key")

  def saveAmazonSignature: CheckBuilder[RegexCheckType, String, String] =
    regex(signaturePattern).saveAs("amazonSignature")

  def saveAMZMetaRequestId: CheckBuilder[RegexCheckType, String, String] =
    regex(metaRequestIDPattern).saveAs("amazonMetaRequestID")

  def saveAMZMetaSessionId: CheckBuilder[RegexCheckType, String, String] =
    regex(metaSesssionIDPattern).saveAs("amazonMetaSessionID")

  def savePolicy: CheckBuilder[RegexCheckType, String, String] =
    regex(policyPattern).saveAs("policy")

}
