/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.trackingconsent

import java.net.URLDecoder
import play.api.libs.json.Json
import play.api.mvc.{Cookie, RequestHeader}

class UserPreferences {
  // For PoC purposes only - do not use as-is.
  // Check security implications for parsing arbitrary cookie content using play-json
  // This logic would live in a micro-library included by play-frontend-hmrc and play-ui e.g. hmrc/tracking-consent-client
  // The logic should mirror and be kept in sync with the
  // logic in https://github.com/hmrc/tracking-consent-frontend/blob/master/app/uk/gov/hmrc/trackingconsentfrontend/assets/src/domain/userPreferencesFactory.ts
  // Unrecognised cookie versions should result in measurement and settings set to false
  def preferences(implicit request: RequestHeader): Preferences =
    request.cookies.get("userConsent") flatMap { (userConsentRaw: Cookie) =>
      Json.parse(URLDecoder.decode(userConsentRaw.value, "UTF-8")).asOpt[UserConsent]
    } map (_.preferences).getOrElse(Preferences(measurement = false, settings = false))
}
