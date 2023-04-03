/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acceptance.pages

import acceptance.conf.TestConfiguration
import org.openqa.selenium.{By, WebDriver}
import org.scalatestplus.selenium.Page

import scala.jdk.CollectionConverters._

object OnlineServicesTermsPage extends BasePage {
  override val url: String =
    TestConfiguration.url("help-frontend") + "/terms-and-conditions/online-services"

  def withLang(lang: String): Page = new Page {
    override val url: String =
      TestConfiguration.url("help-frontend") + s"/terms-and-conditions/online-services?lang=$lang"
  }

  def subHeadings(implicit webDriver: WebDriver): Seq[String] = {
    val subHeadingElements = webDriver.findElements(By.className("govuk-heading-l")).asScala
    subHeadingElements.toSeq.map(_.getText)
  }

  def links(implicit webDriver: WebDriver): Seq[String] = {
    val linkElements = webDriver
      .findElement(By.className("govuk-main-wrapper"))
      .findElements(By.className("govuk-link"))
      .asScala
    linkElements.toSeq.map(_.getAttribute("href"))
  }

  def languageOfPage(implicit webDriver: WebDriver): String = webDriver
    .findElement(By.tagName("html"))
    .getAttribute("lang")

  def switchLanguageToEnglish(implicit webDriver: WebDriver): Unit =
    webDriver.findElement(By.partialLinkText("English")).click()

  def switchLanguageToWelsh(implicit webDriver: WebDriver): Unit =
    webDriver.findElement(By.partialLinkText("Cymraeg")).click()
}
