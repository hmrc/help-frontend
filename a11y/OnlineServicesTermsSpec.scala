import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Messages, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import uk.gov.hmrc.helpfrontend.config.AppConfig
import uk.gov.hmrc.helpfrontend.views.html.OnlineServicesTermsPage
import uk.gov.hmrc.scalatestaccessibilitylinter.AccessibilityMatchers

class OnlineServicesTermsSpec extends AnyWordSpec with Matchers with AccessibilityMatchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm" -> false,
        "metrics.enabled" -> false
      ).build()

  "Online Services Terms and conditions page" must {
    "pass accessibility checks" in {
      given AppConfig = app.injector.instanceOf[AppConfig]
      given fakeRequest: FakeRequest[?] = FakeRequest()
      given messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
      given Messages = messagesApi.preferred(fakeRequest)

      val onlineServicesTermsPage: OnlineServicesTermsPage = app.injector.instanceOf[OnlineServicesTermsPage]

      onlineServicesTermsPage().toString() should passAccessibilityChecks
    }
  }

}
