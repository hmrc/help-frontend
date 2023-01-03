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

package uk.gov.hmrc.helpfrontend.controllers

import play.api.i18n.I18nSupport
import play.api.mvc._
import play.api.i18n.Lang
import uk.gov.hmrc.helpfrontend.config.AppConfig
import uk.gov.hmrc.helpfrontend.views.html._
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class HelpController @Inject() (
  appConfig: AppConfig,
  mcc: MessagesControllerComponents,
  termsAndConditionsPage: TermsAndConditionsPage,
  onlineServicesTermsPage: OnlineServicesTermsPage,
  cookiesPage: CookiesPage
)(implicit val executionContext: ExecutionContext)
    extends FrontendController(mcc)
    with I18nSupport {

  implicit val config: AppConfig = appConfig

  val index: Action[AnyContent] = Action { implicit request =>
    Redirect(
      appConfig.helpPageUrl,
      SEE_OTHER
    )
  }

  val termsAndConditions: Action[AnyContent] = Action { implicit request =>
    Ok(termsAndConditionsPage())
  }

  val cookies: Action[AnyContent] = Action { implicit request =>
    Redirect(
      appConfig.cookieSettingsUrl,
      MOVED_PERMANENTLY
    )
  }

  val cookieDetails: Action[AnyContent] = Action { implicit request =>
    Ok(cookiesPage())
  }

  private def maybeChangeLang[A](newLang: Option[String])(action: Action[A]): Action[A] =
    Action.async(action.parser) { request =>
      Try(Lang(newLang.get)) match {
        case Success(lang) if supportedLangs.availables.contains(lang) => Future(Redirect(request.path).withLang(lang))
        case _                                                         => action(request)
      }
    }

  def onlineServicesTerms(lang: Option[String] = None): Action[AnyContent] = maybeChangeLang(lang.map(_.take(2))) {
    Action { implicit request =>
      Ok(onlineServicesTermsPage())
    }
  }
}
