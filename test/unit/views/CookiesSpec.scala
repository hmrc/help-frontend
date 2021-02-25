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
import unit.helpers.JsoupHelpers

class CookiesSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with MockitoSugar with JsoupHelpers {
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
    "Google Analytics"             should {
      "have a heading of 'How cookies are used'" in new Fixture {
        view.select("#cookies-are-used").text mustBe "How cookies are used"
      }
      "have a heading of 'Measuring website usage (Google Analytics)'" in new Fixture {
        view.select("#cookies-website-usage").text mustBe "Measuring website usage (Google Analytics)"
      }
      "have a paragraph of 'Google Analytics software collects information about how you use the site. " +
        "This is done to help make sure the site is meeting the needs of its users and to help make improvements.'" in new Fixture {
          val paragraph: String = "Google Analytics software collects information about how you use the site." +
            " This is done to help make sure the site is meeting the needs of its users and to help make improvements."

          view.select("#cookies-analytics-paragraph-1").text mustBe paragraph
        }
      "have a paragraph of 'Google Analytics stores information about:' and have the following bullets " +
        "'the pages you visit - how long you spend on each page', 'how you got to the site', " +
        "'what you click on while you’re visiting the site'" in new Fixture {
          val paragraph = "Google Analytics stores information about:"
          val bullet1   = "the pages you visit - how long you spend on each page"
          val bullet2   = "how you got to the site"
          val bullet3   = "what you click on while you’re visiting the site"

          view.select("#cookies-analytics-paragraph-2").text mustBe paragraph
          view.select("#cookies-analytics-bullets1").text mustBe bullet1
          view.select("#cookies-analytics-bullets2").text mustBe bullet2
          view.select("#cookies-analytics-bullets3").text mustBe bullet3
        }

      "have a paragraph of 'We don’t collect or store your personal information'" +
        "(eg your name or address) so this information can’t be used to identify who you are.' " +
        "and have the following text 'Google isn’t allowed to use or share our analytics data.'" in new Fixture {
          val paragraph: String = "We don’t collect or store your personal information " +
            "(eg your name or address) so this information can’t be used to identify who you are."

          view.select("#cookies-analytics-paragraph-3").text mustBe paragraph
          view
            .select("#cookies-analytics-inset-text")
            .first
            .text mustBe "Google isn’t allowed to use or share our analytics data."
        }

      "have a table of 'The following cookies are used:' with the table headings 'Name', 'Purpose', 'Expires'" in new Fixture {
        val table: Elements = view.select("#cookies-analytics-table")
        table.select("caption").text mustBe "The following cookies are used:"
        view.verifyTableHeadings(tableId = "cookies-analytics-table", expectedTableHeadingsText)
      }

      "have the following answers in the table '_gat', 'This helps us identify how you " +
        "use GOV.UK so we can make the site better', '10 minutes'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("_gat", "This helps us identify how you use GOV.UK so we can make the site better", "10 minutes")

          view.verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 1)
        }
      "have the following answers in the table 'GDS_successEvents and GDS_analyticsTokens', " +
        "'These help us identify how you use GOV.UK so we can make the site better', '4 months'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "GDS_successEvents and GDS_analyticsTokens",
            "These help us identify how you use GOV.UK so we can make the site better",
            "4 months"
          )

          view.verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 2)
        }
      "have the following answers in the table '_ga', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '2 years'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("_ga", "This helps us identify how you use GOV.UK so we can make the site better", "2 years")

          view.verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 3)
        }
      "have the following answers in the table 'ga_nextpage_params', " +
        "'This stores data to be sent to Google on the next page you request', 'When you close your browser'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "ga_nextpage_params",
            "This stores data to be sent to Google on the next page you request",
            "When you close your browser"
          )

          view.verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 4)
        }
      "have a pragraph of 'You can opt out of Google Analytics cookies (opens in new tab).'" in new Fixture {
        view
          .select("#cookies-analytics-opt-out")
          .text mustBe "You can opt out of Google Analytics cookies (opens in new tab)."
      }
      "have a link of 'https://tools.google.com/dlpage/gaoptout'" in new Fixture {
        view.select("a[href*=\"tools.google\"]").first.attr("href") mustBe "https://tools.google.com/dlpage/gaoptout"
      }
    }
    "Comparing different versions" should {
      "have a heading of 'Comparing different versions of a webpage (Optimizely)'" in new Fixture {
        view.select("#cookies-versions-heading").text mustBe "Comparing different versions of a webpage (Optimizely)"
      }

      "have a paragraph of 'Optimizely software is used to test different versions of a webpage (or webpages) " +
        "to see which performs better. This is done to help make improvements and ensure the site is " +
        "meeting the needs of its users.'" in new Fixture {

          val paragraph: String = "Optimizely software is used to test different versions of a webpage (or webpages) " +
            "to see which performs better. This is done to help make improvements and ensure the site is meeting the needs of its users."

          view.select("#cookies-versions-paragraph-1").text mustBe paragraph
        }
      "have a table of 'The following cookies are used:' and have the following headings" in new Fixture {
        val table: Elements = view.select("#cookies-versions-table")

        table.select("caption").text mustBe "The following cookies are used:"
        view.verifyTableHeadings(tableId = "cookies-versions-table", expectedTableHeadingsText)
      }

      "have the following answers in the table 'optimizelyEndUserId', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '6 months'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "optimizelyEndUserId",
            "This helps us identify how you use GOV.UK so we can make the site better",
            "6 months"
          )

          view.verifyTableRowText(tableId = "cookies-versions-table", expectedTableAnswersText, rowNumber = 1)
        }
      "have the following answers in the table 'optimizelyRedirectData', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '5 seconds'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "optimizelyRedirectData",
            "This helps us identify how you use GOV.UK so we can make the site better",
            "5 seconds"
          )

          view.verifyTableRowText(tableId = "cookies-versions-table", expectedTableAnswersText, rowNumber = 2)
        }
    }
    "Sessions"                     should {
      val storesSession: String     = "Stores session data"
      val whenBrowserCloses: String = "When you close your browser"

      "have a heading of 'Sessions'" in new Fixture {
        view.select("#cookies-sessions-heading").text mustBe "Sessions"
      }
      "have a table of 'A cookie is set to record your session activity.' " +
        "with the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
          view.select("#cookies-sessions-paragraph").text mustBe "A cookie is set to record your session activity."
          view.verifyTableHeadings("cookies-sessions-table", expectedTableHeadingsText)
        }

      "have the following table answers in the table 'mdtp', 'Stores session data', 'When you close your browser'" in new Fixture {
        val expectedTableAnswersText: List[String] = List("mdtp", storesSession, whenBrowserCloses)

        view.verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 1)
      }
      "have the following table answers in the table 'mdtpp', 'Stores session data', 'When you close your browser'" in new Fixture {
        val expectedTableAnswersText: List[String] = List("mdtpp", storesSession, whenBrowserCloses)

        view.verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 2)
      }
      "have the following table answers in the table 'mdtpdf', 'Stores session data', 'When you close your browser'" in new Fixture {
        val expectedTableAnswersText: List[String] = List("mdtpdf", storesSession, whenBrowserCloses)

        view.verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 3)
      }
      "have the following table answers in the table 'PLAY_FLASH', 'Stores session data', 'When you close your browser'" in new Fixture {
        val expectedTableAnswersText: List[String] = List("PLAY_FLASH", storesSession, whenBrowserCloses)

        view.verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 4)
      }
      "have the following table answers in the table 'remme', 'This is used to uniquely identify a user on a device trying to go through the 2SV challenge', '7 days'" in new Fixture {
        val expectedTableAnswersText: List[String] = List(
          "remme",
          "This is used to uniquely identify a user on a device trying to go through the 2SV challenge",
          "7 days"
        )

        view.verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 5)
      }
    }
    "Our introductory message"     should {
      "have the following heading" in new Fixture {
        view.select("#cookies-introductory-heading").text mustBe "Our introductory message"
      }

      "have the following paragraph 'You may see a pop-up welcome message when you first visit. " +
        "A cookie is stored so that your computer knows you’ve seen it and knows not to show it again.' " +
        "and have the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
          val paragraph: String = "You may see a pop-up welcome message when you first visit. " +
            "A cookie is stored so that your computer knows you’ve seen it and knows not to show it again."

          view.select("#cookies-introductory-paragraph").text mustBe paragraph
          view.verifyTableHeadings("cookies-introductory-table", expectedTableHeadingsText)
        }
      "have the following table answers in the table 'seen_cookie_message', " +
        "'Saves a message to let us know that you have seen our cookie message', '1 month'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "seen_cookie_message",
            "Saves a message to let us know that you have seen our cookie message",
            "1 month"
          )

          view.verifyTableRowText("cookies-introductory-table", expectedTableAnswersText, rowNumber = 1)
        }
    }
    "User research banner"         should {
      "have the following heading" in new Fixture {
        view.select("#cookies-user-research-heading").text mustBe "User research banner"
      }
      "have the following paragraph 'You may see a banner about user research when you visit. " +
        "A cookie is stored so that your computer knows when you have chosen not to see it again.' " +
        "and have the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
          val paragraph: String = "You may see a banner about user research when you visit. " +
            "A cookie is stored so that your computer knows when you have chosen not to see it again."

          view.select("#cookies-user-research-paragraph").text mustBe paragraph
          view.verifyTableHeadings("cookies-user-research-table", expectedTableHeadingsText)
        }
      "have the following table answers in the table 'mdtpurr', " +
        "'Saves a message to let us know that you no longer want to see it again.', '1 month'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("mdtpurr", "Saves a message to let us know that you no longer want to see it again.", "1 month")

          view.verifyTableRowText("cookies-user-research-table", expectedTableAnswersText, rowNumber = 1)
        }
    }
    "Our satisfaction survey"      should {
      "have the following heading 'Our satisfaction survey'" in new Fixture {
        view.select("#cookies-satisfaction-survey-heading").text mustBe "Our satisfaction survey"
      }
      "have a link of 'http://www.surveymonkey.com/'" in new Fixture {
        view.select("a[href*=\"surveymonkey\"]").first.attr("href") mustBe "http://www.surveymonkey.com/"
      }
      "have the following paragraph 'SurveyMonkey (opens in new tab) is used to collect responses to the survey." +
        "If you take part, SurveyMonkey will save extra cookies to your computer to track your progress through it.'" +
        "and have the following headings 'Name', 'Purpose', 'Expires'" in new Fixture {
          val paragraph: String = "SurveyMonkey (opens in new tab) is used to collect responses to the survey. " +
            "If you take part, SurveyMonkey will save extra cookies to your computer to track your progress through it."

          view.select("#cookies-satisfaction-survey-paragraph").text mustBe paragraph
          view.verifyTableHeadings("cookies-satisfaction-survey-table", expectedTableHeadingsText)
        }
      "have the following table answers in the table 'mbox', " +
        "'This is used to keep your progress through the survey', '30 minutes'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("mbox", "This is used to keep your progress through the survey", "30 minutes")

          view.verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 1)
        }
      "have the following table answers in the table 'SSOE', " +
        "'This is used for testing different content and features on the survey website to help make it better', " +
        "'When you close your browser'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "SSOE",
            "This is used for testing different content and features on the survey " +
              "website to help make it better",
            "When you close your browser"
          )

          view.verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 2)
        }
      "have the following table answers in the table 'TS5159a2 and TSd0b041', " +
        "'These are used to manage survey traffic by sending your computer to a specific server', " +
        "'When you close your browser'" in new Fixture {
          val expectedTableAnswersText: List[String] = List(
            "TS5159a2 and TSd0b041",
            "These are used to manage survey traffic by sending your computer to a " +
              "specific server",
            "When you close your browser"
          )
          view.verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 3)
        }
      "have the following table answers in the table 'ep201', " +
        "'This is used to help us identify how you use the survey', " +
        "'30 minutes'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("ep201", "This is used to help us identify how you use the survey", "30 minutes")

          view.verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 4)
        }
      "have the following table answers in the table 'ep202', " +
        "'This works with ep201 to help us identify how you use the survey', " +
        "'1 year'" in new Fixture {
          val expectedTableAnswersText: List[String] =
            List("ep202", "This works with ep201 to help us identify how you use the survey", "1 year")

          view.verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 5)
        }
    }
  }

  "The page footer" must {
    "include a link to the 'Is this page not working properly?' form" in new Fixture {
      val links: Elements =
        view.select("a[href~=problem_reports_nonjs]")

      links                    must have size 1
      links.first.attr("href") must include("service=help-frontend")
      links.first.text mustBe "Is this page not working properly? (opens in new tab)"
    }
  }

  trait Fixture {
    implicit val fakeRequest: FakeRequest[_] = FakeRequest()

    implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

    implicit val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

    implicit val messages: Messages = messagesApi.preferred(fakeRequest)

    val expectedTableHeadingsText: List[String] = List("Name", "Purpose", "Expires")

    val cookiesPage: CookiesPage = app.injector.instanceOf[CookiesPage]

    val view: HtmlFormat.Appendable = cookiesPage()
  }
}
