package functional

import org.jsoup.Jsoup
import org.scalatest._
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.api.Play.current
import play.api.libs.ws.WS
import play.api.test._

class HelpTests extends WordSpec with ShouldMatchers with BeforeAndAfterAll with ScalaFutures with DefaultAwaitTimeout with IntegrationPatience {

  val port = 9000

  trait RunningServer {
    def resource(path: String) = WS.url(s"http://localhost:$port" + path).get().futureValue
  }

  val application = FakeApplication(additionalConfiguration =
    Map(
      "auditing.enabled" -> false,
      "auditing.traceRequests" -> false
    )
  )

  val server = TestServer(port, application)

  override protected def beforeAll() = {
    server.start()
  }

  override protected def afterAll() = {
    server.stop()
  }

  "The help service" should {
    "respond with a 200 status code when the service is OK" in new RunningServer {
      resource("/ping/ping").status shouldBe 200
    }

    "return a cookies page on /help/cookies" in new RunningServer {
      Jsoup.parse(resource("/help/cookies").body).getElementsByTag("h1").text() shouldBe "Cookies"
    }

    "return a cookies page on /help/privacy" in new RunningServer {
      Jsoup.parse(resource("/help/privacy").body).getElementsByTag("h1").text() shouldBe "Privacy policy"
    }

    "return a cookies page on /help/terms-and-conditions" in new RunningServer {
      Jsoup.parse(resource("/help/terms-and-conditions").body).getElementsByTag("h1").text() shouldBe "Terms and conditions"
    }
  }
}
