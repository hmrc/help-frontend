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

package acceptance.specs

import acceptance.pages.{EnglishCookiesPage, PrivacyPage, TermsAndConditionsPage}

class HelpFeature extends BaseSpec {
  Feature("Help") {
    Scenario("Navigate to the cookies page") {
      Given("I go to the English version of the cookies page")
      go to EnglishCookiesPage

      Then("I am shown the cookies page")
      eventually {
        pageTitle should be("Cookies")
      }

      And("I am shown cookie related content")
      EnglishCookiesPage.cookiesInfoText shouldBe "Small files (known as ‘cookies’) are put onto your computer to collect information about how you browse the site."
    }

    Scenario("Navigate to the privacy policy page") {
      Given("I go to the privacy policy page")
      go to PrivacyPage

      Then("I am shown the privacy policy page")
      eventually {
        pageTitle should be("Privacy policy")
      }

      And("I am shown privacy related content")
      PrivacyPage.personalInfoText shouldBe "This page explains what kind of personal information HM Revenue and Customs (HMRC) holds about you, how it’s protected and how you can find out about it."
    }

    Scenario("Navigate to the terms and conditions page") {
      Given("I go to the terms and conditions page")
      go to TermsAndConditionsPage

      Then("I am shown the terms and conditions page")
      eventually {
        pageTitle should be("Terms and conditions")
      }

      And("I am shown terms and conditions related content")
      TermsAndConditionsPage.disclaimerText shouldBe "Disclaimer"
    }
  }
}
