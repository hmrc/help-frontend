package support.page

import org.openqa.selenium.{By, WebDriver}
import org.scalatest.selenium.{Page, WebBrowser}
import support.Env

trait WebPage extends Page with WebBrowser {
  implicit def webDriver: WebDriver = Env.driver
  def isCurrentPage: Boolean
  def heading: String = webDriver.findElement(By.tagName("h1")).getText
  def bodyText: String = tagName("body").element.text
}
