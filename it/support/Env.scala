package support

import java.net.URL
import java.util.logging.Level

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxOptions, FirefoxProfile}
import org.openqa.selenium.logging.{LogType, LoggingPreferences}
import org.openqa.selenium.remote.{CapabilityType, DesiredCapabilities, RemoteWebDriver}


object Env {
  var host: String = Option(System.getProperty("environment")) match {
    case _ => {
      Option(System.getProperty("host")).getOrElse("http://localhost:6001")
    }
  }

  lazy val driver: WebDriver = System.getProperty("browser", "chrome-local").toLowerCase match  {
    case "chrome-local"             =>
      val caps = DesiredCapabilities.chrome()
      val logPrefs = new LoggingPreferences
      logPrefs.enable(LogType.BROWSER, Level.ALL)
      caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
      new ChromeDriver(caps)

    case "firefox-local"            =>
      val options: FirefoxOptions = new FirefoxOptions()
      val profile: FirefoxProfile  = new FirefoxProfile()
      profile.setAcceptUntrustedCertificates(true)
      options.setProfile(profile)
      options.setAcceptInsecureCerts(true)
      new FirefoxDriver(options)

    case "remote-chrome" => createRemoteChrome

    case _ => throw new IllegalStateException("please specify a valid 'browser' e.g. firefox, chrome")
  }

  sys addShutdownHook {
    driver.quit()
  }

  def createRemoteChrome: WebDriver = {
    val caps = DesiredCapabilities.chrome()
    val logPrefs = new LoggingPreferences
    logPrefs.enable(LogType.BROWSER, Level.ALL)
    caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
    val options = new ChromeOptions()
    new RemoteWebDriver(new URL(s"http://localhost:4444/wd/hub"), options.merge(caps))
  }
}
