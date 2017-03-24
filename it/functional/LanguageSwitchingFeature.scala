package functional

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, urlEqualTo}
import org.junit.runner.RunWith
import org.scalatest.TestData
import org.scalatest.junit.JUnitRunner
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Mode}
import support.StubbedFeatureSpec
import support.page.CookiesPage

@RunWith(classOf[JUnitRunner])
class LanguageSwitchingFeature extends StubbedFeatureSpec {
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "application.langs" -> "en,cy",
          "Test.enableLanguageSwitching" -> true
        )
      )
      .in(Mode.Test)
      .build()

  feature("Language Switching") {
    scenario("Switch from English to Welsh in the cookies page") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the cookies page")
      goOn(CookiesPage)

      And("The language switching link is visible")
      CookiesPage.languageSwitchingLink.isDefined shouldBe true

      And("I click on the switch language link")
      click on linkText("Cymraeg")

      Then("I see the page in Welsh")
      CookiesPage.cookiesInfoText shouldBe "Caiff ffeiliau bach (a elwir yn 'cwcis') eu gosod ar eich cyfrifiadur i gasglu gwybodaeth am sut yr ydych yn pori'r wefan."

      And("I click on the switch language link")
      click on linkText("English")

      Then("I see the page in English")
      CookiesPage.cookiesInfoText shouldBe "Small files (known as 'cookies') are put onto your computer to collect information about how you browse the site."
    }
  }
}


@RunWith(classOf[JUnitRunner])
class LanguageSwitchingDisabledFeature extends StubbedFeatureSpec {
  implicit override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "application.langs" -> "en,cy",
          "Test.enableLanguageSwitching" -> false
        )
      )
      .in(Mode.Test)
      .build()

  //  override lazy val app = FakeApplication(
  //    additionalConfiguration = Map(
  //      "application.langs" -> "en,cy",
  //      "Test.enableLanguageSwitching" -> false
  //    )
  //  )

  feature("Language Switching disabled") {
    scenario("Navigate to the cookies page with language switching disabled") {
      WireMock.stubFor(post(urlEqualTo("/write/audit")).willReturn(aResponse().withStatus(200)))

      Given("I go to the cookies page")
      goOn(CookiesPage)

      Then("I do not see the language switching link")
      CookiesPage.languageSwitchingLink shouldBe None
    }
  }
}