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
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.mvc.Cookie
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.helpfrontend.controllers.HelpController
import unit.helpers.JsoupHelpers

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class HelpControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with JsoupHelpers {
  private val fakeRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/")

  private val fakeRequestWithLang: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest("GET", "/").withCookies(Cookie("PLAY_LANG", "cy"))

  "GET /" should {
    "return 200" in new ControllerContext() {
      val result = controller.cookieDetails(fakeRequest)

      status(result) mustBe Status.OK
    }

    "return HTML" in new ControllerContext() {
      val result = controller.cookieDetails(fakeRequest)

      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content" in new ControllerContext() {
      val result  = controller.cookieDetails(fakeRequest)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Cookies"
    }
  }

  "GET /terms-and-conditions/online-services" should {
    "return 200 if enabled in config" in new ControllerContext() {
      val result = controller.onlineServicesTerms()(fakeRequest)

      status(result) mustBe Status.OK
    }

    "return HTML if enabled in config" in new ControllerContext() {
      val result = controller.onlineServicesTerms()(fakeRequest)

      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content if enabled in config" in new ControllerContext() {
      val result  = controller.onlineServicesTerms()(fakeRequest)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "HMRC Online Services Terms & Conditions"
    }

    "return 200 if a lang parameter is included in the request" in new ControllerContext() {
      val result = controller.onlineServicesTerms(Some("cym"))(fakeRequest)

      status(result) mustBe Status.OK
    }

    "set the language cookie and render content in English when the url parameter is lang=eng" in new ControllerContext() {
      val result  = controller.onlineServicesTerms(Some("eng"))(fakeRequest)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "HMRC Online Services Terms & Conditions"

      val awaitResult = Await.result(result, 2 second)
      awaitResult.newCookies.find(_.name == "PLAY_LANG").map(_.value) mustBe Some("en")
    }

    "set the language cookie and render content in Welsh when the url parameter is lang=cym" in new ControllerContext() {
      val result  = controller.onlineServicesTerms(Some("cym"))(fakeRequest)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Telerau ac Amodau Gwasanaethau ar-lein CThEM"

      val awaitResult = Await.result(result, 2 second)
      awaitResult.newCookies.find(_.name == "PLAY_LANG").map(_.value) mustBe Some("cy")
    }

    "render content in current language if the lang parameter value is unsupported" in new ControllerContext() {
      val result  = controller.onlineServicesTerms(Some("foo"))(fakeRequestWithLang)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Telerau ac Amodau Gwasanaethau ar-lein CThEM"

      val awaitResult = Await.result(result, 2 second)
      awaitResult.newCookies.find(_.name == "PLAY_LANG").map(_.value) mustBe Some("cy")
    }
  }

  private class ControllerContext {

    val applicationFromContext = new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"     -> false,
        "metrics.enabled" -> false
      )
      .build()
    val controller             = applicationFromContext.injector.instanceOf[HelpController]

  }

}
