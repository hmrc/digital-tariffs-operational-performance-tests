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

package uk.gov.hmrc.perftests.digitaltariffs

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType
import uk.gov.hmrc.performance.conf.ServicesConfiguration

trait DigitalTariffsPerformanceTestRunner extends ServicesConfiguration {

  val deleteAllCasesStaging: Boolean = applicationConfig.getBoolean("services.deleteAllCases")

  val adminBaseUrl: String                          = baseUrlFor("tariff-classification-frontend")
  val authStubBaseUrl: String                       = baseUrlFor("auth-login-stub") + "/auth-login-stub"
  val traderUiBaseUrl: String                       = baseUrlFor("binding-tariff-trader-frontend") + "/advance-tariff-application"
  val operatorUiBaseUrl: String                     = baseUrlFor("tariff-classification-frontend") + "/manage-tariff-classifications"
  val bindingTariffClassificationBackendUrl: String = baseUrlFor("binding-tariff-classification")

  val eoriNumber = "AA000111222"

  def saveCsrfToken: CheckBuilder[RegexCheckType, String, String] =
    regex(_ => csrfPattern).saveAs("csrfToken")

  private val csrfPattern = """<input type="hidden" name="csrfToken" value="([^"]+)"""

}
