package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.http.protocol.HttpProtocolBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.OperatorUiRequests._
import uk.gov.hmrc.perftests.digitaltariffs.operatorui.StrideAuthRequests._

class OperatorUiSimulation extends PerformanceTestRunner with DigitalTariffsPerformanceTestRunner {

  override val httpProtocol: HttpProtocolBuilder = {
    buildHttpProtocol(url = adminBaseUrl)
  }

  setup("operationalUI", "HMRC Operator reviews a BTI application") withRequests(
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

  runSimulation()
  
}
