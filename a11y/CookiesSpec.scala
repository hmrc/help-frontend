import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Messages, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import uk.gov.hmrc.helpfrontend.config.AppConfig
import uk.gov.hmrc.helpfrontend.views.html.CookiesPage
import uk.gov.hmrc.scalatestaccessibilitylinter.AccessibilityMatchers

class CookiesSpec extends AnyWordSpec with Matchers with AccessibilityMatchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm" -> false,
        "metrics.enabled" -> false
      ).build()

  "Cookies section" must {
    "pass accessibility checks" in {
      given AppConfig = app.injector.instanceOf[AppConfig]
      given fakeRequest: FakeRequest[?] = FakeRequest()
      given messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
      given Messages = messagesApi.preferred(fakeRequest)

      val cookiesPage: CookiesPage = app.injector.instanceOf[CookiesPage]
      cookiesPage().toString() should passAccessibilityChecks
    }
  }

}
