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
import io.gatling.http.Predef.flushCookieJar
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiCorrespondenceRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiLiabilityRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiMiscRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.StrideAuthRequests._

class OperatorUiSimulation extends PerformanceTestRunner with DigitalTariffsPerformanceTestRunner {

  exec(flushCookieJar)

  val strideAuthSignInRequests =
    Seq(
      getProtectedPageNoSession,
      getStrideSignIn,
      getIdpSignInPage,
      postIdpSignInPage,
      getSignInRedirect,
      postIdpResponseToStride
    )

  //  setup("atar", "ATaR Case").withRequests(
  //    //Create ATaR Case
  //    getGovGatewaySignIn,
  //    postGovGatewaySignIn,
  //    getTraderStartPage,
  //    getInformationYouNeed,
  //    getInformationMadePublic,
  //    getGoodsName,
  //    postGoodsName,
  //    postGoodsDescription,
  //    postConfidentialInfo,
  //    postUploadSupportingDocument,
  //    postAreYouSendingASample,
  //    postHaveYouFoundCommodityCode,
  //    postLegalChallenge,
  //    postPreviousRulingReference,
  //    postSimilarRuling,
  //    postRegisterForEori,
  //    postEnterContactDetails,
  //    postCheckYourAnswers,
  //    //    flushAllCookies,
  //    // Stride Auth Sign In
  //    getProtectedPageNoSession,
  //    getStrideSignIn,
  //    getIdpSignInPage,
  //    postIdpSignInPage,
  //    getSignInRedirect,
  //    postIdpResponseToStride,
  //    // HMRC Operator UI journey
  //    getStartPage,
  //    getGatewayQueue,
  //    getFindValidCaseReference,
  //    getCaseTraderDetails,
  //    getActionCase,
  //    getReleaseToAQueue,
  //    getReleaseConfirmation,
  //    getOpenCases,
  //    getAssignCase,
  //    getChangeCaseStatusRefer,
  //    getReferCase,
  //    getFileUpload,
  //    getReferConfirmation
  //  )

  val liabilityRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getOpenLiability,
        getNewLiability,
        postNewLiability,
        getLiabilityRef,
        getCaseToAction,
        getActionCase,
        postActionCase,
        getReleaseToAQueue,
        postReleaseToAQueue,
        getReleaseConfirmation,
        getOpenCases,
        getAssignCase,
        getCaseToAction
      )

  setup("liability", "Liability application")
    .withRequests(liabilityRequests: _*)

  val correspondenceRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getCorrespondenceCase,
        getCreateNewCorrespondenceCase,
        postCreateNewCorrespondenceCase,
        getCorrespondenceRefViaAdvancedSearch,
        getReleaseCorrespondenceCase,
        postReleaseCorrespondenceCase,
        getChooseReleaseTeam,
        postChooseReleaseTeam,
        getCaseReleasedConfirmation
      )

  setup("correspondence", "Correspondence case")
    .withRequests(correspondenceRequests: _*)

  val miscCaseRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getMiscCase,
        getCreateMiscCase,
        postCreateNewMiscCase,
        getNewMiscCaseViaAdvancedSearch,
        getMiscChooseReleaseTeam,
        postMiscChooseReleaseTeam,
        getMiscCaseReleasedConfirmation
      )

  setup("misc", "Misc case")
    .withRequests(miscCaseRequests: _*)

  runSimulation()
}
