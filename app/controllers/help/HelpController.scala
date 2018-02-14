/*
 * Copyright 2018 HM Revenue & Customs
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

package controllers.help

import uk.gov.hmrc.play.frontend.controller.{FrontendController, UnauthorisedAction}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

object HelpController extends FrontendController {

  val cookies = UnauthorisedAction { implicit request =>
    Ok(views.html.cookies())
  }

  val termsAndConditions = UnauthorisedAction { implicit request =>
    Ok(views.html.t_and_c())
  }

  val privacyPolicy = UnauthorisedAction { implicit request =>
    Ok(views.html.privacy_policy())
  }

}
