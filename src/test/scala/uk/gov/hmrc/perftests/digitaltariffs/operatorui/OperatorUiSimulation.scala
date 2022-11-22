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

import io.gatling.core.Predef.exec
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.http.Predef.flushCookieJar
import io.gatling.http.protocol.HttpProtocolBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.AuthRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiCorrespondenceRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiLiabilityRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiMiscRequest.{getMiscCase, postCreateMisc}
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.StrideAuthRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.TraderUiRequests._

class OperatorUiSimulation extends PerformanceTestRunner with DigitalTariffsPerformanceTestRunner {

  override val httpProtocol: HttpProtocolBuilder =
    buildHttpProtocol(url = adminBaseUrl)

  setup("OperationalUIATaR", "HMRC Operator refers a ATaR Case").withRequests(
    //Create ATaR Case
    getGovGatewaySignIn,
    postGovGatewaySignIn,
    getTraderStartPage,
    getInformationYouNeed,
    getInformationMadePublic,
    getGoodsName,
    postGoodsName,
    postGoodsDescription,
    postConfidentialInfo,
    postUploadSupportingDocument,
    postAreYouSendingASample,
    postHaveYouFoundCommodityCode,
    postLegalChallenge,
    postPreviousRulingReference,
    postSimilarRuling,
    postRegisterForEori,
    postEnterContactDetails,
    postCheckYourAnswers,
    //    flushAllCookies,
    // Stride Auth Sign In
    getProtectedPageNoSession,
    getStrideSignIn,
    getIdpSignInPage,
    postIdpSignInPage,
    getSignInRedirect,
    postIdpResponseToStride,
    // HMRC Operator UI journey
    getStartPage,
    getGatewayQueue,
    findValidCaseReference,
    getCaseTraderDetails,
    getActionCase,
    getReleaseToAQueue,
    getReleaseConfirmation,
    getOpenCases,
    getAssignCase,
    getChangeCaseStatusRefer,
    getReferCase,
    getFileUpload,
    getReferConfirmation
  )

  setup("OperationalUILiability", "HMRC Operator reviews a Liability application") withRequests(
    // Stride Auth Sign In
    getProtectedPageNoSession,
    getStrideSignIn,
    getIdpSignInPage,
    postIdpSignInPage,
    getSignInRedirect,
    postIdpResponseToStride,
    // HMRC Operator UI journey
    getStartPage,
    getOpenLiability,
    getNewLiability,
    postNewLiability,
    getLiabilityRef,
    getCaseToAction,
    getActionCase,
    getReleaseToAQueue,
    getReleaseConfirmation,
    getOpenCases,
    getAssignCase,
    getCaseToAction
  )

  setup("OperationalUICorrespondence", "HMRC Operator creates a Correspondence case") withRequests(
    // Stride Auth Sign In
    getProtectedPageNoSession,
    getStrideSignIn,
    getIdpSignInPage,
    postIdpSignInPage,
    getSignInRedirect,
    postIdpResponseToStride,
    // HMRC Operator UI journey
    getStartPage,
    getCorrespondenceCase,
    postCreateCorrespondence,
    postReleaseCorrespondenceCase,
    postChooseReleaseTeam,
    getCaseReleasedConfirmation
  )

  setup("OperationalUIMisc", "HMRC Operator creates a Misc case") withRequests(
    // Stride Auth Sign In
    getProtectedPageNoSession,
    getStrideSignIn,
    getIdpSignInPage,
    postIdpSignInPage,
    getSignInRedirect,
    postIdpResponseToStride,
    // HMRC Operator UI journey
    getStartPage,
    getMiscCase,
    postCreateMisc,
    postChooseReleaseTeam,
    getCaseReleasedConfirmation
  )

  runSimulation()
}
