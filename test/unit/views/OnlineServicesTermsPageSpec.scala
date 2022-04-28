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

package unit.views

import org.jsoup.nodes.Element
import org.mockito.scalatest.MockitoSugar
import org.openqa.selenium.By
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Messages, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpfrontend.config.AppConfig
import uk.gov.hmrc.helpfrontend.views.html.OnlineServicesTermsPage
import unit.helpers.JsoupHelpers

import collection.JavaConverters._

class OnlineServicesTermsPageSpec
    extends AnyWordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with MockitoSugar
    with JsoupHelpers {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"                                   -> false,
        "metrics.enabled"                               -> false,
        "onlineTermsAndConditions.enablePage"           -> true,
        "onlineTermsAndConditions.enableLanguageToggle" -> true
      )
      .build()

  "Online Services Terms and conditions" must {
    "have a title of 'HMRC Online Services Terms & conditions – GOV.UK'" in new Fixture {
      view.select("title").text mustBe "HMRC Online Services Terms & conditions – GOV.UK"
    }

    "have a heading of 'HMRC Online Services Terms & conditions'" in new Fixture {
      view.select("#online-services-terms-heading").text mustBe "HMRC Online Services Terms & conditions"
    }

    "have the expected section headings" in new Fixture {
      view.select(".govuk-heading-l").asScala.toList.map(_.text()) mustBe List(
        "All individuals, organisations and agents",
        "Additional points for individuals",
        "Additional points for organisations",
        "Additional points for agents, accountant and representatives",
        "All Shared Workspace users"
      )
    }

    "display a Welsh language toggle when enabled in config" in new Fixture {
      view.select(".hmrc-language-select").size() mustBe 1
    }
  }

  trait Fixture {

    implicit val fakeRequest: FakeRequest[_] = FakeRequest()

    implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

    implicit val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

    implicit val messages: Messages = messagesApi.preferred(fakeRequest)

    val page: OnlineServicesTermsPage = app.injector.instanceOf[OnlineServicesTermsPage]

    val view: HtmlFormat.Appendable = page()
  }

}
