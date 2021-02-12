package uk.gov.hmrc.perftests.digitaltariffs.operatorui

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.digitaltariffs.DigitalTariffsPerformanceTestRunner

object OperatorUiMiscRequest extends DigitalTariffsPerformanceTestRunner {


  def getMiscCase: HttpRequestBuilder = {
    http("Get to Misc")
      .get(s"$operatorUiBaseUrl/all-open-cases?activeSubNav=sub_nav_miscellaneous_tab")
      .check(status.is(200))
      .check(css("input[name='csrfToken']", "value").saveAs("csrfToken"))
  }

  def postCreateMisc:HttpRequestBuilder = {
    http("Create Correspondence")
      .post(s"$operatorUiBaseUrl/create-new-miscellaneous")
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Misc Description")
      .formParam("contactName", "Contact Name")
      .formParam("caseType", "IB")
      .check(status.is(200))
      .check((regex("8[0-9]{8}").saveAs("caseRef")))
  }

}


