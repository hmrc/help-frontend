package support.behaviour

import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.selenium.WebBrowser
import org.scalatest.selenium.WebBrowser.{go => goo}
import org.scalatest.{Assertions, ShouldMatchers}
import support.page.WebPage
import support.Env


trait NavigationSugar extends WebBrowser with Eventually with Assertions with ShouldMatchers with IntegrationPatience {
  implicit def webDriver: WebDriver = Env.driver

  def goOn(page: WebPage): Unit = {
    go(page)
    on(page)
  }

  def go(page: WebPage): Unit = {
    goo to page
  }

  def on(page: WebPage): Unit = {
    waitForPageToLoad()
    assert(page.isCurrentPage, s"Page was not loaded: $page")
  }

  def waitForPageToLoad(): WebElement = {
    val wait = new WebDriverWait(webDriver, 30)
    wait.until(
      new ExpectedCondition[WebElement] {
        override def apply(d: WebDriver): WebElement = {
          d.findElement(By.tagName("body"))
        }
      }
    )
  }

  def anotherTabIsOpened(): Unit = {
    webDriver.getWindowHandles.size() should be(2)
  }
}