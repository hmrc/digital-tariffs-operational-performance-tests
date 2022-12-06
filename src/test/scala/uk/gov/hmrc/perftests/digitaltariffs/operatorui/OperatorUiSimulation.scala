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
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.AuthRequests.{getAuthLoginStub, postAuthLoginStub}
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.CorrespondenceRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.LiabilityRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.MiscRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.AtarRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.StrideAuthRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.TraderUiRequests._

class OperatorUiSimulation extends  DigitalTariffsPerformanceTestRunner {

  val flushAllCookies =
    exec(flushCookieJar).actionBuilders.head

  val strideAuthSignInRequests =
    Seq(
      getProtectedPageNoSession,
      getStrideSignIn,
      getIdpSignInPage,
      postIdpSignInPage,
      getSignInRedirect,
      postIdpResponseToStride
    )


  val hmrcOperatorUIJourney =
    Seq(
      getStartPage,
      getGatewayQueue,
      //      getFindValidCaseReference,
      getCaseTraderDetails,
      getActionThisCase,
      postActionThisCase,
      getReleaseToAQueue,
      postReleaseToAQueue,
      getReleaseConfirmation,
      getOpenCases,
      getAssignCase,
      postAssignCase,
      getChangeCaseStatusRefer,
      postChangeCaseStatusRefer,
      getReferCase,
      postReferCase,
      getFileUpload,
      postFileUpload,
      getReferConfirmation
    )

  //  val atarJourney = createAtarCase ++ strideAuthSignInRequests ++ hmrcOperatorUIJourney

  val liabilityRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getOpenLiability,
        getNewLiability,
        postNewLiability,
        //        getLiabilityRef,
        getLiabilityCase,
        getActionThisCase,
        postActionThisCase,
        getReleaseToAQueue,
        postReleaseToAQueue,
        getReleaseConfirmation,
        getOpenCases,
        getAssignCase,
      )

  val correspondenceRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getCorrespondenceCase,
        getCreateNewCorrespondenceCase,
        postCreateNewCorrespondenceCase,
        //        getCorrespondenceRefViaAdvancedSearch,
        getReleaseCorrespondenceCase,
        postReleaseCorrespondenceCase,
        getChooseReleaseTeam,
        postChooseReleaseTeam,
        getCaseReleasedConfirmation
      )

  val miscCaseRequests =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getMiscCase,
        getCreateMiscCase,
        postCreateNewMiscCase,
        //        getNewMiscCaseViaAdvancedSearch,
        getMiscChooseReleaseTeam,
        postMiscChooseReleaseTeam,
        getMiscCaseReleasedConfirmation
      )

  setup("atar", "ATAR")
    .withActions(
      getAuthLoginStub,
      postAuthLoginStub,
      getYourApplicationsAndRulingsPage,
      getInformationYouNeed,
      getInformationMadePublic,
      getGoodsName,
      postGoodsName,
      getGoodsDescription,
      postGoodsDescription,
      getConfidentialInfo,
      postConfidentialInfo,
      getUploadSupportingDocument,
      postUploadSupportingDocument,
      getAreYouSendingASample,
      postAreYouSendingASample,
      getHaveYouFoundCommodityCode,
      postHaveYouFoundCommodityCode,
      getLegalChallenge,
      postLegalChallenge,
      getPreviousRulingReference,
      postPreviousRulingReference,
      getSimilarRuling,
      postSimilarRuling,
      getRegisterForEori,
      postRegisterForEori,
      getEnterContactDetails,
      postEnterContactDetails,
      getCheckYourAnswers,
      postCheckYourAnswers,
      getConfirmationPage,

      flushAllCookies,
      getProtectedPageNoSession,
      getStrideSignIn,
      getIdpSignInPage,
      postIdpSignInPage,
      getSignInRedirect,
      postIdpResponseToStride,
      getStartPage,
      getGatewayQueue,
      //      getFindValidCaseReference,
      getCaseTraderDetails,
      getActionAtarCase,
      postActionAtarCase,
      getAtarReleaseToAQueue,
      postAtarReleaseToAQueue,
      getAtarReleaseConfirmation,
      getAtarOpenCases,
      getAtarAssignCase,
      postAtarAssignCase,
      getAtarChangeCaseStatusRefer,
      postAtarChangeCaseStatusRefer,
      getAtarReferCase,
      postAtarReferCase,
      getAtarFileUpload,
      postAtarFileUpload,
      getAtarReferConfirmation
    )

  setup("liability", "Liability")
    .withRequests(liabilityRequests: _*)

  setup("correspondence", "Correspondence")
    .withRequests(correspondenceRequests: _*)

  setup("misc", "Misc")
    .withRequests(miscCaseRequests: _*)

  runSimulation()
}
