/*
 * Copyright 2021 HM Revenue & Customs
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

package unit.views

import org.mockito.scalatest.MockitoSugar
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Messages, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpfrontend.config.AppConfig
import uk.gov.hmrc.helpfrontend.views.html.TermsAndConditionsPage
import uk.gov.hmrc.scalatestaccessibilitylinter.AccessibilityMatchers
import unit.helpers.JsoupHelpers

class TermsAndConditionsSpec
    extends AnyWordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with MockitoSugar
    with JsoupHelpers
    with AccessibilityMatchers {
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"     -> false,
        "metrics.enabled" -> false
      )
      .build()

  "Terms and conditions page" must {
    "pass accessibility checks" in new Fixture {
      view.toString() must passAccessibilityChecks
    }
  }

  trait Fixture {
    implicit val fakeRequest: FakeRequest[_] = FakeRequest()

    implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

    implicit val messages: Messages = messagesApi.preferred(fakeRequest)

    implicit val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

    val termsAndConditionsPage: TermsAndConditionsPage = app.injector.instanceOf[TermsAndConditionsPage]

    val view: HtmlFormat.Appendable = termsAndConditionsPage()
  }
}
