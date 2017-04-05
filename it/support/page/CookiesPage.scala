package support.page

import org.openqa.selenium.By
import support.Env

sealed trait CookiesPage extends WebPage {
  override val url: String = Env.host + "/help/cookies"

  override def isCurrentPage: Boolean = heading == cookiesHeading

  def otherLanguage: String

  def cookiesHeading: String

  def cookiesInfoText: String = webDriver.findElement(By.id("cookies-info")).getText

  def switchLanguage() = webDriver.findElement(By.linkText(otherLanguage)).click()

  def hasLanguageSwitchingLink: Boolean = !webDriver.findElements(By.linkText(otherLanguage)).isEmpty
}

object EnglishCookiesPage extends CookiesPage {
  val otherLanguage: String = "Cymraeg"

  val cookiesHeading: String = "Cookies"
}

object WelshCookiesPage extends CookiesPage {
  val otherLanguage: String = "English"

  val cookiesHeading: String = "Cwcis"
}
