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

package unit.controllers

import org.jsoup.Jsoup
import org.scalatest.matchers.must.Matchers
import play.api.http.Status
import play.api.test.Helpers._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatest.wordspec.AnyWordSpec
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import uk.gov.hmrc.helpfrontend.controllers.HelpController
import unit.helpers.JsoupHelpers

class HelpControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with JsoupHelpers {
  def applicationFromConfig(pageEnabled: Boolean = true) =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"                         -> false,
        "metrics.enabled"                     -> false,
        "onlineTermsAndConditions.enablePage" -> pageEnabled
      )
      .build()

  private val fakeRequest = FakeRequest("GET", "/")

  "GET /" should {
    "return 200" in {
      val app        = applicationFromConfig(true)
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.cookieDetails(fakeRequest)

      status(result) mustBe Status.OK
    }

    "return HTML" in {
      val app        = applicationFromConfig()
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.cookieDetails(fakeRequest)

      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content" in {
      val app        = applicationFromConfig()
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.cookieDetails(fakeRequest)
      val content    = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Cookies"
    }
  }

  "GET /terms-and-conditions/online-services" should {
    "return 200 if enabled in config" in {
      val app        = applicationFromConfig()
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)

      status(result) mustBe Status.OK
    }

    "return HTML if enabled in config" in {
      val app        = applicationFromConfig()
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)

      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content if enabled in config" in {
      val app        = applicationFromConfig()
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)
      val content    = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "HMRC Online Services Terms & conditions"
    }

    "return 404 if disabled in config" in {
      val app        = applicationFromConfig(pageEnabled = false)
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)

      status(result) mustBe Status.NOT_FOUND
    }

    "return HTML if disabled in config" in {
      val app        = applicationFromConfig(pageEnabled = false)
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)

      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content if disabled in config" in {
      val app        = applicationFromConfig(pageEnabled = false)
      val controller = app.injector.instanceOf[HelpController]
      val result     = controller.onlineServicesTerms(fakeRequest)
      val content    = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Page not found"
    }
  }

}
