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
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.controller.inject.open.OpenInjectionStep
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef.flushCookieJar
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.simulation.{Journey, PerformanceTestRunner}
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.AtarRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.AuthRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.CorrespondenceRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.DeleteAllCasesRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.LiabilityRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.MiscRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.StrideAuthRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorUI.TraderUiRequests._

import scala.util.Random

class AtarSimulation extends PerformanceTestRunner with DigitalTariffsPerformanceTestRunner {

  private val RNG = new Random

  val deleteCasesScenario: PopulationBuilder =
    scenario("Clear down and delete cases from environment")
      .feed(Iterator.continually(Map("currentTime" -> System.currentTimeMillis().toString)))
      .feed(Iterator.continually(Map("random" -> Math.abs(RNG.nextInt()))))
      .exec(deleteAllCases())
      .inject(atOnceUsers(1))

  val flushAllCookies: ActionBuilder =
    exec(flushCookieJar).actionBuilders.head

  override def withInjectedLoad(journeys: Seq[Journey]): Seq[PopulationBuilder] = {
    val fullJourneys = super.withInjectedLoad(journeys)

    if (deleteAllCasesStaging) {
      deleteCasesScenario +: fullJourneys
    } else {
      fullJourneys
    }
  }

  val strideAuthSignInRequests =
    Seq(
      getProtectedPageNoSession,
      getStrideSignIn,
      getIdpSignInPage,
      postIdpSignInPage,
      getSignInRedirect,
      postIdpResponseToStride
    )

  val liabilityRequests: Seq[HttpRequestBuilder] =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getOpenLiability,
        getNewLiability,
        postNewLiability,
        getLiabilityCase,
        getActionThisCase,
        postActionThisCase,
        getReleaseToAQueue,
        postReleaseToAQueue,
        getReleaseConfirmation,
        getOpenCases,
        getAssignCase
      )

  val correspondenceRequests: Seq[HttpRequestBuilder] =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getCorrespondenceCase,
        getCreateNewCorrespondenceCase,
        postCreateNewCorrespondenceCase,
        getReleaseCorrespondenceCase,
        postReleaseCorrespondenceCase,
        getChooseReleaseTeam,
        postChooseReleaseTeam,
        getCaseReleasedConfirmation
      )

  val miscCaseRequests: Seq[HttpRequestBuilder] =
    strideAuthSignInRequests ++
      Seq(
        getStartPage,
        getMiscCase,
        getCreateMiscCase,
        postCreateNewMiscCase,
        getMiscChooseReleaseTeam,
        postMiscChooseReleaseTeam,
        getMiscCaseReleasedConfirmation
      )

  setup("traderUIReferCase", "TraderUI")
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
      getCaseTraderDetails,
      getReleaseOrSuppressAtarCase,
      postReleaseOrSuppressAtarCase,
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
