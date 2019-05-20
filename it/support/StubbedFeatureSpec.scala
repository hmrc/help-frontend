package support

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.scalatest._
import org.scalatestplus.play.OneServerPerTest
import support.behaviour.NavigationSugar

trait StubbedFeatureSpec
  extends FeatureSpec
    with GivenWhenThen
    with OneServerPerTest
    with BeforeAndAfter
    with BeforeAndAfterEach
    with BeforeAndAfterAll
    with NavigationSugar
    with OptionValues {

  override lazy val port = 6001

  val stubPort = 11111
  val stubHost = "localhost"
  val wireMockServer: WireMockServer = new WireMockServer(wireMockConfig().port(stubPort))

  override def beforeAll(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(stubHost, stubPort)
  }

  override def afterAll(): Unit = {
    wireMockServer.stop()
  }

  override def beforeEach(): Unit = {
    webDriver.manage().deleteAllCookies()
    WireMock.reset()
  }
}
