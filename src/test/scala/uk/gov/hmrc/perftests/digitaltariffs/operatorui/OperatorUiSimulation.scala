package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.http.protocol.HttpProtocolBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiLiabilityRequest._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.StrideAuthRequests._

class OperatorUiSimulation extends PerformanceTestRunner with DigitalTariffsPerformanceTestRunner {

  override val httpProtocol: HttpProtocolBuilder = {
    buildHttpProtocol(url = adminBaseUrl)
  }

  setup("operationalUI", "HMRC Operator reviews a ATaR application") withRequests(
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
    getCarsQueue,
    getStartPage,
    getGatewayQueue,
    getCarsQueue,
    getElmQueue,
    getActQueue,
    getCapQueue,
    getMyCases,
    findValidCaseReference,
    getCaseTraderDetails,
    getCaseApplicationDetails,
    getCaseAttachments,
    getCaseActivity,
    getCaseKeywords,
    getCaseRuling,
    searchPage,
    getQueryResultPage
  )

  setup("OperationalUIATaR", "HMRC Operator refers a ATaR Case") withRequests(
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
    getReleaseConfirmation
  )

  setup("operationalUILiability", "HMRC Operator reviews a Liability application") withRequests(
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
    getLiabilityRef,
    getCaseToAction,
    getActionCase,
    getReleaseToAQueue,
    getReleaseConfirmation,
    getOpenCases,
    getAssignCase,
    getCaseTraderDetails


  )

  runSimulation()

}
