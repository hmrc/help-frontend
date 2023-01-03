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

package acceptance.specs

import acceptance.pages.{EnglishCookiesPage, OnlineServicesTermsPage, WelshCookiesPage}

class LanguageSwitchingFeature extends BaseSpec {
  Feature("Language Switching") {
    Scenario("Switch from English to Welsh in the cookies page") {
      When("I go to the English version of the cookies page")
      go to EnglishCookiesPage

      Then("The language switching link is visible")
      eventually {
        EnglishCookiesPage.hasLanguageSwitchingLink shouldBe true
      }

      When("I click on the switch language link")
      EnglishCookiesPage.switchLanguage

      Then("I see the page in Welsh")
      WelshCookiesPage.cookiesInfoText shouldBe "Caiff ffeiliau bach (a elwir yn ‘cwcis’) eu gosod ar eich cyfrifiadur i gasglu gwybodaeth am sut yr ydych yn pori’r wefan."

      When("I click on the switch language link")
      WelshCookiesPage.switchLanguage

      Then("I see the page in English")
      EnglishCookiesPage.cookiesInfoText shouldBe "Small files (known as ‘cookies’) are put onto your computer to collect information about how you browse the site."
    }

    Scenario(
      "Switch from English to Welsh on the online services T&Cs page which was opened with ?lang=eng query param"
    ) {
      Given("I go to the English version of the online services T&Cs page using the lang query param")
      go to OnlineServicesTermsPage.withLang("eng")
      OnlineServicesTermsPage.languageOfPage shouldBe "en"

      When("I switch language to Welsh")
      OnlineServicesTermsPage.switchLanguageToWelsh

      Then("I see the page in Welsh")
      OnlineServicesTermsPage.languageOfPage shouldBe "cy"
    }

    Scenario(
      "Switch from Welsh to English on the online services T&Cs page which was opened with ?lang=cym query param"
    ) {
      Given("I go to the Welsh version of the online services T&Cs page using the lang query param")
      go to OnlineServicesTermsPage.withLang("cym")
      OnlineServicesTermsPage.languageOfPage shouldBe "cy"

      When("I switch language to English")
      OnlineServicesTermsPage.switchLanguageToEnglish

      Then("I see the page in English")
      OnlineServicesTermsPage.languageOfPage shouldBe "en"
    }
  }
}
