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
import uk.gov.hmrc.helpfrontend.views.html.OnlineServicesTermsPage
import unit.helpers.JsoupHelpers

import scala.jdk.CollectionConverters._

class OnlineServicesTermsPageSpec
    extends AnyWordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with MockitoSugar
    with JsoupHelpers {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"     -> false,
        "metrics.enabled" -> false
      )
      .build()

  "Online Services Terms and conditions" must {
    "have a title of 'HMRC Online Services Terms & Conditions – GOV.UK'" in new Fixture {
      view.select("title").first().text mustBe "HMRC Online Services Terms & Conditions – GOV.UK"
    }

    "have a heading of 'HMRC Online Services Terms & Conditions'" in new Fixture {
      view.select("#online-services-terms-heading").text mustBe "HMRC Online Services Terms & Conditions"
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

    "display a Welsh language toggle" in new Fixture {
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
