/*
 * Copyright 2022 HM Revenue & Customs
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

sealed trait CookiesPage extends BasePage {
  override val url: String =
    TestConfiguration.url("help-frontend") + "/cookie-details"

  def otherLanguage: String

  def cookiesHeading: String

  def cookiesInfoText(implicit webDriver: WebDriver): String = webDriver.findElement(By.id("cookies-info")).getText

  def switchLanguage(implicit webDriver: WebDriver): Unit =
    webDriver.findElement(By.partialLinkText(otherLanguage)).click()

  def hasLanguageSwitchingLink(implicit webDriver: WebDriver): Boolean =
    !webDriver.findElements(By.partialLinkText(otherLanguage)).isEmpty
}

object EnglishCookiesPage extends CookiesPage {
  val otherLanguage: String  = "Cymraeg"
  val cookiesHeading: String = "Cookies"
}

object WelshCookiesPage extends CookiesPage {
  val otherLanguage: String  = "English"
  val cookiesHeading: String = "Cwcis"
}
