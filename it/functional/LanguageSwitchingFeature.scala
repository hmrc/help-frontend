package functional

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, urlEqualTo}
import org.scalatest.TestData
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Mode}
import support.StubbedFeatureSpec
import support.page.{EnglishCookiesPage, WelshCookiesPage}

class LanguageSwitchingFeature extends StubbedFeatureSpec {
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "application.langs" -> "en,cy",
          "enableLanguageSwitching" -> true
        )
      )
      .in(Mode.Test)
      .build()

  feature("Language Switching") {
    scenario("Switch from English to Welsh in the cookies page") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      When("I go to the English version of the cookies page")
      goOn(EnglishCookiesPage)

      Then("The language switching link is visible")
      EnglishCookiesPage.hasLanguageSwitchingLink shouldBe true

      When("I click on the switch language link")
      EnglishCookiesPage.switchLanguage()

      Then("I see the page in Welsh")
      on(WelshCookiesPage)
      WelshCookiesPage.cookiesInfoText shouldBe "Caiff ffeiliau bach (a elwir yn 'cwcis') eu gosod ar eich cyfrifiadur i gasglu gwybodaeth am sut yr ydych yn pori'r wefan."

      When("I click on the switch language link")
      WelshCookiesPage.switchLanguage()

      Then("I see the page in English")
      on(EnglishCookiesPage)
      EnglishCookiesPage.cookiesInfoText shouldBe "Small files (known as 'cookies') are put onto your computer to collect information about how you browse the site."
    }
  }
}

class LanguageSwitchingDisabledFeature extends StubbedFeatureSpec {
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "application.langs" -> "en,cy",
          "enableLanguageSwitching" -> false
        )
      )
      .in(Mode.Test)
      .build()

  feature("Language Switching disabled") {
    scenario("Navigate to the cookies page with language switching disabled") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the cookies page")
      goOn(EnglishCookiesPage)

      Then("I do not see the language switching link")
      EnglishCookiesPage.hasLanguageSwitchingLink shouldBe false
    }
  }
}
