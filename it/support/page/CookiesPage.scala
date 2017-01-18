package support.page

import org.openqa.selenium.{By, WebElement}
import support.Env

import scala.util.Try

object CookiesPage extends WebPage {
  override val url: String = Env.host + "/help/cookies"
  override def isCurrentPage: Boolean = heading == "Cookies"

  def cookiesInfoText: String = webDriver.findElement(By.id("cookies-info")).getText
  def languageSwitchingLink: Option[WebElement] = Try(webDriver.findElement(By.linkText("Cymraeg"))).toOption
}
