package functional

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, urlEqualTo}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import support.StubbedFeatureSpec
import support.page.{CookiesPage, PrivacyPage, TermsAndConditionsPage}

@RunWith(classOf[JUnitRunner])
class HelpFeature extends StubbedFeatureSpec {
  feature("Help") {
    scenario("Navigate to the cookies page") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the cookies page")
      goOn(CookiesPage)

      Then("I am shown the cookies page")
      CookiesPage.cookiesInfoText shouldBe "Small files (known as 'cookies') are put onto your computer to collect information about how you browse the site."
    }

    scenario("Navigate to the privacy policy page") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the privacy policy page")
      goOn(PrivacyPage)

      Then("I am shown the privacy policy page")
      PrivacyPage.personalInfoText shouldBe "This page explains what kind of personal information HM Revenue and Customs (HMRC) holds about you, how it's protected and how you can find out about it."
    }

    scenario("Navigate to the terms and conditions page") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the terms and conditions page")
      goOn(TermsAndConditionsPage)

      Then("I am shown the terms and conditions page")
      TermsAndConditionsPage.disclaimerText shouldBe "Disclaimer"
    }
  }
}
