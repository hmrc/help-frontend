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

import org.jsoup.select.Elements
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
import uk.gov.hmrc.helpfrontend.views.html.CookiesPage
import uk.gov.hmrc.scalatestaccessibilitylinter.AccessibilityMatchers
import unit.helpers.JsoupHelpers

class CookiesSpec
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

  "Cookies section" must {

    "have a heading of 'Cookies'" in new Fixture {
      view.select("#cookies-heading").text mustBe "Cookies"
    }

    "have a paragraph of 'Small files (known as ‘cookies’) are put onto your computer to collect " +
      "information about how you browse the site.'" in new Fixture {
        val paragraph: String = "Small files (known as ‘cookies’) are put onto your computer to " +
          "collect information about how you browse the site."

        view.select("#cookies-info").text mustBe paragraph
      }

    "have a paragraph of 'Cookies are used to:' and bullets of " +
      "'measure how you use the website so it can be updated and improved based on your needs', " +
      "'remember the notifications you‘ve seen so that you’re not shown them again'" in new Fixture {
        val bullet1 = "measure how you use the website so it can be updated and improved based on your needs"
        val bullet2 = "remember the notifications you’ve seen so that you’re not shown them again"

        view.select("#cookies-usages").text mustBe "Cookies are used to:"
        view.select("#cookies-usages-bullets1").text mustBe bullet1
        view.select("#cookies-usages-bullets2").text mustBe bullet2
      }

    "have a notice of 'GOV.UK cookies aren’t used to identify you personally.' and the following text " +
      "'You’ll normally see a message on the site before a cookie is stored on your computer.', " +
      "'Find out more about how to manage cookieslink opens in a new window.'" in new Fixture {
        val paragraph: String = "You’ll normally see a message on the site before a cookie is stored on your computer."

        view.select("#cookies-inset-text").first.text mustBe "GOV.UK cookies aren’t used to identify you personally."
        view.select("#cookies-paragraph-1").text mustBe paragraph
        view.select("#cookies-more-about").text mustBe "Find out more about how to manage cookies (opens in new tab)."
      }

    "have a link of 'http://www.aboutcookies.org/'" in new Fixture {
      view.select("a[href*=\"aboutcookies\"]").first.attr("href") mustBe "http://www.aboutcookies.org/"
    }
  }

  "How cookies are used section" should {

    "Google Analytics" should {
      "have the heading 'Measuring website usage (Google Analytics)'" in new Fixture {
        view.select("#cookies-website-usage").text mustBe "Measuring website usage (Google Analytics)"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-analytics-paragraph-1").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-analytics-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "_gat",
          "GDS_successEvents and GDS_analyticsTokens",
          "_ga",
          "ga_nextpage_params"
        )

        view.verifyTableContainsCookieName("cookies-analytics-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-analytics-table", defaultTableCaption)
      }
    }

    "Comparing different versions" should {
      "have the heading 'Comparing different versions of a webpage (Optimizely)'" in new Fixture {
        view.select("#cookies-versions-heading").text mustBe "Comparing different versions of a webpage (Optimizely)"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-versions-paragraph-1").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-versions-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "optimizelyEndUserId",
          "optimizelyRedirectData"
        )

        view.verifyTableContainsCookieName("cookies-versions-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-versions-table", defaultTableCaption)
      }
    }

    "Allow additional cookies" should {
      "have the heading 'Sessions'" in new Fixture {
        view.select("#cookies-consent-heading").text mustBe "Cookies message"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-consent-paragraph-1").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-consent-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List("userConsent")

        view.verifyTableContainsCookieName("cookies-consent-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-consent-table", defaultTableCaption)
      }
    }

    "Sessions" should {
      "have the heading 'Sessions'" in new Fixture {
        view.select("#cookies-sessions-heading").text mustBe "Sessions"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-sessions-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-sessions-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "mdtp",
          "mdtpp",
          "mdtpdf",
          "PLAY_FLASH",
          "remme",
          "mdtpdi"
        )

        view.verifyTableContainsCookieName("cookies-sessions-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-sessions-table", defaultTableCaption)
      }
    }

    "Our introductory message" should {
      "have the heading 'Our introductory message" in new Fixture {
        view.select("#cookies-introductory-heading").text mustBe "Our introductory message"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-introductory-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-introductory-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List("seen_cookie_message")

        view.verifyTableContainsCookieName("cookies-introductory-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-introductory-table", defaultTableCaption)
      }
    }

    "User research banner" should {
      "have the heading 'User research banner" in new Fixture {
        view.select("#cookies-user-research-heading").text mustBe "User research banner"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-user-research-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-user-research-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List("mdtpurr")

        view.verifyTableContainsCookieName("cookies-user-research-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-user-research-table", defaultTableCaption)
      }
    }

    "Our satisfaction survey" should {
      "have the heading 'Our satisfaction survey'" in new Fixture {
        view.select("#cookies-satisfaction-survey-heading").text mustBe "Our satisfaction survey"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-satisfaction-survey-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-satisfaction-survey-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "mbox",
          "SSOE",
          "TS5159a2 and TSd0b041",
          "ep201",
          "ep202"
        )

        view.verifyTableContainsCookieName("cookies-satisfaction-survey-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-satisfaction-survey-table", defaultTableCaption)
      }
    }

    "Digital assistant cookies" should {
      "have the heading 'Digital assistant cookies'" in new Fixture {
        view.select("#cookies-digital-assistant-heading").text mustBe "Digital assistant cookies"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-digital-assistant-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-digital-assistant-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "inqCA_10006330",
          "inqVital_10006330",
          "tcSrv_10006330",
          "inqSession_10006330",
          "inqState_10006330",
          "JSESSIONID"
        )

        view.verifyTableContainsCookieName("cookies-digital-assistant-table", expectedCookies)
      }

      "have no table caption" in new Fixture {
        view.verifyTableCaption("cookies-digital-assistant-table", None)
      }
    }

    "JavaScript detection cookies" should {
      "have the heading 'JavaScript detection'" in new Fixture {
        view.select("#cookies-javascript-detection-heading").text mustBe "JavaScript detection"
      }

      "have a paragraph of descriptive text" in new Fixture {
        view.select("#cookies-javascript-detection-paragraph").size() mustBe 1
      }

      "have a table with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        view.verifyTableHeadings("cookies-javascript-detection-table", expectedTableHeadingsText)
      }

      "have the expected cookies listed in the table" in new Fixture {
        val expectedCookies: List[String] = List(
          "JS‑Detection"
        )

        view.verifyTableContainsCookieName("cookies-javascript-detection-table", expectedCookies)
      }

      "have the expected table caption" in new Fixture {
        view.verifyTableCaption("cookies-javascript-detection-table", defaultTableCaption)
      }
    }

    "The page footer" must {
      "include a link to the 'Is this page not working properly?' form" in new Fixture {
        val links: Elements =
          view.select("a[href~=report-technical-problem]")

        links                    must have size 1
        links.first.attr("href") must include("service=help-frontend")
        links.first.text mustBe "Is this page not working properly? (opens in new tab)"
      }
    }
  }

  trait Fixture {
    implicit val fakeRequest: FakeRequest[_] = FakeRequest()

    implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

    implicit val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

    implicit val messages: Messages = messagesApi.preferred(fakeRequest)

    val expectedTableHeadingsText: List[String] = List("Name", "Purpose", "Expires")

    val defaultTableCaption: Option[String] = Some("The following cookies are used:")

    val cookiesPage: CookiesPage = app.injector.instanceOf[CookiesPage]

    val view: HtmlFormat.Appendable = cookiesPage()
  }

}
