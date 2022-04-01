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

package it

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._

class RedirectIntegrationSpec extends AnyWordSpec with Matchers {

  private val baseProperties = Map("metrics.jvm" -> false, "metrics.enabled" -> false, "auditing.enabled" -> false)

  def buildApp(properties: Map[String, String] = Map.empty) =
    new GuiceApplicationBuilder()
      .configure(baseProperties ++ properties)
      .build()

  private val cookiesRequest = FakeRequest(
    GET,
    "/help/cookies"
  )

  private val cookieDetailsRequest = FakeRequest(
    GET,
    "/help/cookie-details"
  )

  "The /help/cookies endpoint" should {
    "respond with a 301 redirect" in {
      val app = buildApp()

      val response: Result = Await.result(route(app, cookiesRequest).get, 1 seconds)

      response.header.status shouldBe MOVED_PERMANENTLY
    }

    "redirect to tracking consent running locally" in {
      val app = buildApp()

      val response: Result = Await.result(route(app, cookiesRequest).get, 1 seconds)

      val headers = response.header.headers

      headers(
        LOCATION
      ) shouldBe "http://localhost:12345/tracking-consent/cookie-settings"
    }

    "redirect to tracking consent running on the platform" in {
      val app = buildApp(
        Map("platform.frontend.host" -> "https://example.com")
      )

      val response: Result = Await.result(route(app, cookiesRequest).get, 1 seconds)

      val headers = response.header.headers

      headers(
        LOCATION
      ) shouldBe "/tracking-consent/cookie-settings"
    }
  }

  "The /help/cookie-details endpoint" should {
    "respond with a 200" in {
      val app = buildApp()

      val response: Result = Await.result(route(app, cookieDetailsRequest).get, 1 seconds)

      response.header.status shouldBe OK
    }

    "respond with the correct page" in {
      val app = buildApp()

      val content = contentAsString(route(app, cookieDetailsRequest).get)

      content should include("<h1 class=\"govuk-heading-xl\" id=\"cookies-heading\">Cookies</h1>")
    }
  }
}
