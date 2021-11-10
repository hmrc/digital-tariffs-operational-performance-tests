package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object StrideAuthRequests extends DigitalTariffsPerformanceTestRunner {

  private val relayStatePattern = """<input type="hidden" id="RelayState" name="RelayState" value="([^"]+)""""
  private val samlResponsePattern = """<input type="hidden" name="SAMLResponse" value="([^"]+)""""
  private val formUrlPattern = """<form action="([^"]+)""""
  private val strideIdpBaseUrl = s"${baseUrlFor("stride-idp-stub")}"

  private def savePageItem(name: String, pattern: String) = regex(_ => pattern).saveAs(name)

  val getProtectedPageNoSession: HttpRequestBuilder =
    http("Stride Auth - [GET] page without session")
      .get(s"$operatorUiBaseUrl")
      .disableFollowRedirect
      .check(status.is(303))
      .check(header("location").saveAs("protectedPageRedirect"))

  val getStrideSignIn: HttpRequestBuilder =
    if (!runLocal) {
      http("Stride Auth - [GET] redirect to STRIDE Auth")
        .get(s"${baseUrlFor("tariff-classification-frontend")}" + s"$${protectedPageRedirect}")
        .disableFollowRedirect
        .check(status.is(303))
        .check(header("location").saveAs("authRequestRedirect"))
    } else {
      http("Stride Auth - [GET] redirect to STRIDE Auth")
        .get(s"$${protectedPageRedirect}")
        .disableFollowRedirect
        .check(status.is(303))
        .check(header("location").saveAs("authRequestRedirect"))
    }

  val getIdpSignInPage: HttpRequestBuilder =
    http("Stride Auth - [GET] redirect to IdP login")
      .get(s"$${authRequestRedirect}")
      .check(status.is(200))
      .check(savePageItem("relayState", relayStatePattern))
      .check(savePageItem("formUrl", formUrlPattern))

  val postIdpSignInPage: HttpRequestBuilder =
    http("Stride Auth - [POST] IdP login form")
      .post(s"$strideIdpBaseUrl$${formUrl}")
      .disableFollowRedirect
      .formParam("RelayState", s"$${relayState}")
      .formParam("pid", "12345")
      .formParam("status", true)
      .formParam("signature", "valid")
      .formParam("roles", "classification")
      .check(status.is(303))
      .check(header("location").saveAs("signInRedirect"))

  val getSignInRedirect: HttpRequestBuilder =
    http("Stride Auth - [GET] page w/ JS redirect to STRIDE Auth")
      .get(s"$strideIdpBaseUrl$${signInRedirect}")
      .check(status.is(200))
      .check(savePageItem("formUrl", formUrlPattern))
      .check(savePageItem("samlResponse", samlResponsePattern))
      .check(savePageItem("relayState", relayStatePattern))

  val postIdpResponseToStride: HttpRequestBuilder =
    http("Stride Auth - [POST] IdP response to STRIDE Auth")
      .post(s"$${formUrl}")
      .disableFollowRedirect
      .formParam("SAMLResponse", s"$${samlResponse}")
      .formParam("RelayState", s"$${relayState}")
      .check(status.is(303))
      .check(header("location").saveAs("loginRedirect"))
}
