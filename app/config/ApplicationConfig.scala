/*
 * Copyright 2017 HM Revenue & Customs
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

package config

import play.api.Play._
import uk.gov.hmrc.play.config.ServicesConfig

trait AppConfig {
  val assetsPrefix: String
  val analyticsToken: Option[String]
  val analyticsHost: String
  val reportAProblemPartialUrl: String
  val reportAProblemNonJSUrl: String
  def fallbackURLForLanguageSwitcher: String
  def enableLanguageSwitching: Boolean
}

object ApplicationConfig extends AppConfig with ServicesConfig {
  private def loadConfig(key: String) = configuration.getString(key).getOrElse(throw new Exception(s"Missing key: $key"))

  private val contactHost = configuration.getString(s"contact-frontend.host").getOrElse("")

  override lazy val assetsPrefix: String = loadConfig("assets.url") + loadConfig("frontend.assets.version")
  override lazy val analyticsToken: Option[String] = configuration.getString("google-analytics.token")
  override lazy val analyticsHost: String = configuration.getString("google-analytics.host").getOrElse("auto")
  override lazy val reportAProblemPartialUrl = s"$contactHost/contact/problem_reports_ajax"
  override lazy val reportAProblemNonJSUrl = s"$contactHost/contact/problem_reports_nonjs"
  override def fallbackURLForLanguageSwitcher: String = loadConfig(s"$env.languageSwitcher.fallback.url")
  override def enableLanguageSwitching: Boolean = configuration.getBoolean(s"$env.enableLanguageSwitching").getOrElse(false)
}
