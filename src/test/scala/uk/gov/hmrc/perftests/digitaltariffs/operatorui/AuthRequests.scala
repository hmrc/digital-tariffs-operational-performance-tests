package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object AuthRequests extends DigitalTariffsPerformanceTestRunner {

  def getGovGatewaySignIn: HttpRequestBuilder = {
    http("Government Gateway Sign In - GET")
      .get(s"$authStubBaseUrl/gg-sign-in")
      .check(status.is(200))
  }

  def postGovGatewaySignIn: HttpRequestBuilder = {
    http("Government Gateway Sign In - POST")
      .post(s"$authStubBaseUrl/gg-sign-in")
      .formParam("authorityId", "")
      .formParam("redirectionUrl", traderUiBaseUrl)
      .formParam("credentialStrength", "weak")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Individual")
      .formParam("email", "user@test.com")
      .formParam("credentialRole", "User")
      .formParam("enrolment[0].name", "HMRC-ATAR-ORG")
      .formParam("enrolment[0].taxIdentifier[0].name", "EORINumber")
      .formParam("enrolment[0].taxIdentifier[0].value", eoriNumber)
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(200))
  }
}
