package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiCorrespondenceRequest extends DigitalTariffsPerformanceTestRunner {

  def getCorrespondenceCase: HttpRequestBuilder = {
    http("Get to Correspondence")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_correspondence_tab")
      .check(status.is(200))
      .check(css("input[name='csrfToken']", "value").saveAs("csrfToken"))
  }

  def postCreateCorrespondence:HttpRequestBuilder = {
    http("Create Correspondence")
      .post(s"$operatorUiBaseUrl/new-correspondence")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("summary", "Case Description")
      .formParam("source", "a trader")
      .formParam("contactEmail", "abv@abv.com")
      .check(status.is(200))
      .check((regex("8[0-9]{8}").saveAs("caseRef")))
  }

  def postReleaseCorrespondenceCase: HttpRequestBuilder = {
    http("Case released")
      .post(operatorUiBaseUrl + "/release-correspondence-choice?reference=${caseRef}")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("choice", "Yes")
      .check(status.is(200))
  }

  def postChooseReleaseTeam: HttpRequestBuilder = {
    http("Release to a team")
      .post(operatorUiBaseUrl + "/cases/${caseRef}/release")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("queue", "flex")
      .check(status.is(200))
  }

  def getCaseReleasedConfirmation: HttpRequestBuilder ={
    http("Case release confirmation")
      .get(operatorUiBaseUrl + "/cases/${caseRef}/release/confirmation")
      .check(status.is(200))
  }
}


