package support

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxProfile}


object Env {
  var host: String = Option(System.getProperty("environment")) match {
    case _ => Option(System.getProperty("host")).getOrElse("http://localhost:9000")
  }

  lazy val driver: WebDriver = System.getProperty("browser", "chrome").toLowerCase match  {
    case "firefox" =>
      val profile = new FirefoxProfile
      profile.setAcceptUntrustedCertificates(true)
      new FirefoxDriver(profile)
    case "chrome" => new ChromeDriver()
    case _ => throw new IllegalStateException("please specify a valid 'browser' e.g. firefox, chrome")
  }

  sys addShutdownHook {
    driver.quit()
  }
}
