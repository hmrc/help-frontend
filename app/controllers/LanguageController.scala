/*
 * Copyright 2019 HM Revenue & Customs
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

package controllers

import config.ApplicationConfig
import play.api.Logger
import play.api.Play.current
import play.api.i18n.Lang
import play.api.mvc.{Action, LegacyI18nSupport}
import uk.gov.hmrc.play.frontend.controller.FrontendController
import util.LanguageUtils
import play.api.i18n.Messages.Implicits._

trait LanguageController extends FrontendController  with LegacyI18nSupport {

  val english = Lang("en")
  val welsh = Lang("cy")

  def switchToEnglish = switchToLang(english)
  def switchToWelsh = switchToLang(welsh)

  private def switchToLang(lang: Lang) = Action { implicit request =>
    val newLang = if (ApplicationConfig.enableLanguageSwitching) lang else english

    request.headers.get(REFERER) match {
      case Some(referrer) => Redirect(referrer).withLang(newLang).flashing(LanguageUtils.flashWithSwitchIndicator)
      case None =>
        Logger.warn(s"Unable to get the referrer, so sending them to ${ApplicationConfig.fallbackURLForLanguageSwitcher}")
        Redirect(ApplicationConfig.fallbackURLForLanguageSwitcher).withLang(newLang)
    }
  }

}


object LanguageController extends LanguageController
