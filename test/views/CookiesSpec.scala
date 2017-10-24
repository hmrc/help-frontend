/*
 * Copyright 2017 HM Revenue & Customs
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

package views

import play.api.i18n.Lang
import testutil.{Fixture, GenericTestHelper, Link}

class CookiesSpec extends GenericTestHelper {

  trait ViewFixture extends Fixture {
    implicit val lang: Lang = mock[Lang]

    val expectedTableHeadingsText: List[String] = List("Name", "Purpose", "Expires")

    override def view = views.html.cookies()(request, lang, messages)
  }

  "Cookies section" should {

    "have a heading of 'Cookies'" in new ViewFixture {
      getElementTextById("cookies-heading") mustBe "Cookies"
    }

    "have a paragraph of 'Small files (known as 'cookies') are put onto your computer to collect " +
      "information about how you browse the site.'" in new ViewFixture {
      val paragraph: String = "Small files (known as 'cookies') are put onto your computer to collect information about how you browse the site."
      getElementTextById("cookies-info") mustBe paragraph
    }

    "have a paragraph of 'Cookies are used to'" in new ViewFixture {
      getElementTextById("cookies-usages") mustBe "Cookies are used to:"
    }

    "have a bullet of 'measure how you use the website so it can be updated and improved based on your needs'" in new ViewFixture {
      val bullet1 = "measure how you use the website so it can be updated and improved based on your needs"
      getElementTextById("cookies-usages-bullets1") mustBe bullet1
    }

    "have a bullet of 'remember the notifications you've seen so that you're not shown them again'" in new ViewFixture {
      val bullet1 = "remember the notifications you've seen so that you're not shown them again"
      getElementTextById("cookies-usages-bullets2") mustBe bullet1
    }

    "have a notice of 'GOV.UK cookies aren't used to identify you personally." in new ViewFixture {
      doc.getElementsByClass("application-notice").first().text() mustBe "GOV.UK cookies aren't used to identify you personally."
    }
    "have a paragraph of 'You'll normally see a message on the site before a cookie is stored on your computer.'" in new ViewFixture {
      val paragraph: String = "You'll normally see a message on the site before a cookie is stored on your computer."
      getElementTextById("cookies-paragraph-1") mustBe paragraph
    }

    "have a paragraph of 'Find out more about'" in new ViewFixture {
      doc.getElementById("cookies-more-about").text() mustBe "Find out more about how to manage cookieslink opens in a new window."
    }

    "have a link of 'http://www.aboutcookies.org/'" in new ViewFixture {
     findLinkByCssSelector("a[href*=\"aboutcookies\"]").get.href mustBe "http://www.aboutcookies.org/"
    }
  }

  "How cookies are used section" should {
    "Google Analytics" should {
      "have a heading of 'How cookies are used'" in new ViewFixture {
        getElementTextById("cookies-are-used") mustBe "How cookies are used"
      }
      "have a heading of 'Measuring website usage (Google Analytics)'" in new ViewFixture {
        getElementTextById("cookies-website-usage") mustBe "Measuring website usage (Google Analytics)"
      }
      "have a paragraph of 'Google Analytics software collects information about how you use the site. " +
        "This is done to help make sure the site is meeting the needs of its users and to help make improvements.'" in new ViewFixture {
        val paragraph: String = "Google Analytics software collects information about how you use the site." +
          " This is done to help make sure the site is meeting the needs of its users and to help make improvements."
        getElementTextById("cookies-analytics-paragraph-1") mustBe paragraph

      }
      "have a paragraph of 'Google Analytics stores information about:'" in new ViewFixture {
        val paragraph = "Google Analytics stores information about:"
        getElementTextById("cookies-analytics-paragraph-2") mustBe paragraph
      }

      "have a bullet of 'the pages you visit - how long you spend on each page'" in new ViewFixture {
        val bullet1 = "the pages you visit - how long you spend on each page"
        getElementTextById("cookies-analytics-bullets1") mustBe bullet1
      }

      "have a bullet of 'how you got to the site'" in new ViewFixture {
        val bullet2 = "how you got to the site"
        getElementTextById("cookies-analytics-bullets2") mustBe bullet2
      }

      "have a bullet of 'what you click on while you're visiting the site'" in new ViewFixture {
        val bullet3 = "what you click on while you're visiting the site"
        getElementTextById("cookies-analytics-bullets3") mustBe bullet3
      }
      "have a paragraph of 'We don't collect or store your personal information'" +
        "(eg your name or address) so this information can't be used to identify who you are." in new ViewFixture {
        val paragraph: String = "We don't collect or store your personal information " +
          "(eg your name or address) so this information can't be used to identify who you are."
        getElementTextById("cookies-analytics-paragraph-3") mustBe paragraph
      }

      "have a notice of 'Google isn't allowed to use or share our analytics data.'" in new ViewFixture {
        doc.getElementsByClass("application-notice").last().text() mustBe "Google isn't allowed to use or share our analytics data."
      }
      "have a paragraph of 'The following cookies are used:'" in new ViewFixture {
        getElementTextById("cookies-analytics-used") mustBe "The following cookies are used:"
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings(tableId = "cookies-analytics-table", expectedTableHeadingsText)
      }
      "have the following answers in the table '_gat', 'This helps us identify how you " +
        "use GOV.UK so we can make the site better', '10 minutes'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("_gat", "This helps us identify how you use GOV.UK so we can make the site better", "10 minutes")
        verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 1)
      }
      "have the following answers in the table 'GDS_successEvents and GDS_analyticsTokens', " +
        "'These help us identify how you use GOV.UK so we can make the site better', '4 months'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("GDS_successEvents and GDS_analyticsTokens", "These help us identify how you use GOV.UK so we can make the site better", "4 months")
        verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 2)
      }
      "have the following answers in the table '_ga', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '2 years'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("_ga", "This helps us identify how you use GOV.UK so we can make the site better", "2 years")
        verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 3)
      }
      "have the following answers in the table 'ga_nextpage_params', " +
        "'This stores data to be sent to Google on the next page you request', 'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("ga_nextpage_params", "This stores data to be sent to Google on the next page you request", "When you close your browser")
        verifyTableRowText(tableId = "cookies-analytics-table", expectedTableAnswersText, rowNumber = 4)
      }
      "have a pragraph of 'You can opt out of Google Analytics cookieslink opens in a new window.'" in new ViewFixture {
        doc.getElementById("cookies-analytics-opt-out").text() mustBe "You can opt out of Google Analytics cookieslink opens in a new window."
      }
      "have a link of 'https://tools.google.com/dlpage/gaoptout'" in new ViewFixture {
        findLinkByCssSelector("a[href*=\"tools.google\"]").get.href mustBe "https://tools.google.com/dlpage/gaoptout"
      }
    }
    "Comparing different versions" should {
      "have a heading of 'Comparing different versions of a webpage (Optimizely)'" in new ViewFixture {
        getElementTextById("cookies-versions-heading") mustBe "Comparing different versions of a webpage (Optimizely)"
      }

      "have a paragraph of 'Optimizely software is used to test different versions of a webpage (or webpages) " +
        "to see which performs better. This is done to help make improvements and ensure the site is " +
        "meeting the needs of its users.'" in new ViewFixture {

        val paragraph: String = "Optimizely software is used to test different versions of a webpage (or webpages) " +
          "to see which performs better. This is done to help make improvements and ensure the site is meeting the needs of its users."

        getElementTextById("cookies-versions-paragraph-1") mustBe paragraph
      }
      "have a paragraph 'The following cookies are used:'" in new ViewFixture {
        getElementTextById("cookies-versions-paragraph-2") mustBe "The following cookies are used:"
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings(tableId = "cookies-versions-table", expectedTableHeadingsText)
      }
      "have the following answers in the table 'optimizelyEndUserId', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '10 years'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("optimizelyEndUserId", "This helps us identify how you use GOV.UK so we can make the site better", "10 years")
        verifyTableRowText(tableId = "cookies-versions-table", expectedTableAnswersText, rowNumber = 1)
      }
      "have the following answers in the table 'optimizelyRedirectData', " +
        "'This helps us identify how you use GOV.UK so we can make the site better', '5 seconds'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("optimizelyRedirectData", "This helps us identify how you use GOV.UK so we can make the site better", "5 seconds")
        verifyTableRowText(tableId = "cookies-versions-table", expectedTableAnswersText, rowNumber = 2)
      }
    }
    "Sessions" should {
      val storesSession: String = "Stores session data"
      val whenBrowserCloses: String = "When you close your browser"

      "have a heading of 'Sessions'" in new ViewFixture {
        getElementTextById("cookies-sessions-heading") mustBe "Sessions"
      }
      "have a paragraph of 'A cookie is set to record your session activity.'" in new ViewFixture {
        getElementTextById("cookies-sessions-paragraph") mustBe "A cookie is set to record your session activity."
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings("cookies-sessions-table", expectedTableHeadingsText)
      }
      "have the following table answers in the table 'mdtp', 'Stores session data', 'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("mdtp", storesSession, whenBrowserCloses)
        verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 1)
      }
      "have the following table answers in the table 'mdtpp', 'Stores session data', 'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("mdtpp", storesSession, whenBrowserCloses)
        verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 2)
      }
      "have the following table answers in the table 'mdtpdf', 'Stores session data', 'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("mdtpdf", storesSession, whenBrowserCloses)
        verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 3)
      }
      "have the following table answers in the table 'PLAY_FLASH', 'Stores session data', 'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("PLAY_FLASH", storesSession, whenBrowserCloses)
        verifyTableRowText("cookies-sessions-table", expectedTableAnswersText, rowNumber = 4)
      }
    }
    "Our introductory message" should {
      "have the following heading" in new ViewFixture {
        getElementTextById("cookies-introductory-heading") mustBe "Our introductory message"
      }

      "have the following paragraph" in new ViewFixture {
        val paragraph: String = "You may see a pop-up welcome message when you first visit. " +
          "A cookie is stored so that your computer knows you've seen it and knows not to show it again."
        getElementTextById("cookies-introductory-paragraph") mustBe paragraph
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings("cookies-introductory-table", expectedTableHeadingsText)
      }
      "have the following table answers in the table 'seen_cookie_message', " +
        "'Saves a message to let us know that you have seen our cookie message', '1 month'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("seen_cookie_message", "Saves a message to let us know that you have seen our cookie message", "1 month")
        verifyTableRowText("cookies-introductory-table", expectedTableAnswersText, rowNumber = 1)
      }
    }
    "User research banner" should {
      "have the following heading" in new ViewFixture {
        getElementTextById("cookies-user-research-heading") mustBe "User research banner"
      }
      "have the following paragraph" in new ViewFixture {
        val paragraph: String = "You may see a banner about user research when you visit. " +
          "A cookie is stored so that your computer knows when you have chosen not to see it again."
        getElementTextById("cookies-user-research-paragraph") mustBe paragraph
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings("cookies-user-research-table", expectedTableHeadingsText)
      }
      "have the following table answers in the table 'mdtpurr', " +
        "'Saves a message to let us know that you no longer want to see it again.', '1 month'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("mdtpurr", "Saves a message to let us know that you no longer want to see it again.", "1 month")
        verifyTableRowText("cookies-user-research-table", expectedTableAnswersText, rowNumber = 1)
      }
    }
    "Our satisfaction survey" should {
      "have the following heading" in new ViewFixture {
        getElementTextById("cookies-satisfaction-survey-heading") mustBe "Our satisfaction survey"
      }
      "have a link of 'http://www.surveymonkey.com/'" in new ViewFixture {
        findLinkByCssSelector("a[href*=\"surveymonkey\"]").get.href mustBe "http://www.surveymonkey.com/"
      }
      "have the following paragraph" in new ViewFixture {
        val paragraph: String = "SurveyMonkeylink opens in a new window is used to collect responses to the survey. " +
          "If you take part, SurveyMonkey will save extra cookies to your computer to track your progress through it."
        doc.getElementById("cookies-satisfaction-survey-paragraph").text() mustBe paragraph
      }
      "have the following table headings 'Name', 'Purpose', 'Expires'" in new ViewFixture {
        verifyTableHeadings("cookies-satisfaction-survey-table", expectedTableHeadingsText)
      }
      "have the following table answers in the table 'mbox', " +
        "'This is used to keep your progress through the survey', '30 minutes'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("mbox", "This is used to keep your progress through the survey", "30 minutes")
        verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 1)
      }
      "have the following table answers in the table 'SSOE', " +
        "'This is used for testing different content and features on the survey website to help make it better', " +
        "'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("SSOE", "This is used for testing different content and features on the survey website to help make it better", "When you close your browser")
        verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 2)
      }
      "have the following table answers in the table 'TS5159a2 and TSd0b041', " +
        "'These are used to manage survey traffic by sending your computer to a specific server', " +
        "'When you close your browser'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("TS5159a2 and TSd0b041", "These are used to manage survey traffic by sending your computer to a specific server", "When you close your browser")
        verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 3)
      }
      "have the following table answers in the table 'ep201', " +
        "'This is used to help us identify how you use the survey', " +
        "'30 minutes'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("ep201", "This is used to help us identify how you use the survey", "30 minutes")
        verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 4)
      }
      "have the following table answers in the table 'ep202', " +
        "'This works with ep201 to help us identify how you use the survey', " +
        "'1 year'" in new ViewFixture {
        val expectedTableAnswersText: List[String] = List("ep202", "This works with ep201 to help us identify how you use the survey", "1 year")
        verifyTableRowText("cookies-satisfaction-survey-table", expectedTableAnswersText, rowNumber = 5)
      }
    }
  }
}

