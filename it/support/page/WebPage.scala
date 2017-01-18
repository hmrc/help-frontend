package support.page

import org.openqa.selenium.WebDriver
import org.scalatest.ShouldMatchers
import org.scalatest.selenium.{Page, WebBrowser}
import support.Env

trait WebPage extends Page with WebBrowser with ShouldMatchers {
  implicit def webDriver: WebDriver = Env.driver
  def isCurrentPage: Boolean
  def heading: String = tagName("h1").element.text
  def bodyText: String = tagName("body").element.text
}