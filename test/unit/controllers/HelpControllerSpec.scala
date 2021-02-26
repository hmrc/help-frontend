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
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"     -> false,
        "metrics.enabled" -> false
      )
      .build()

  private val fakeRequest = FakeRequest("GET", "/")

  private val controller = app.injector.instanceOf[HelpController]

  "GET /" should {
    "return 200" in {
      val result = controller.cookies(fakeRequest)
      status(result) mustBe Status.OK
    }

    "return HTML" in {
      val result = controller.cookies(fakeRequest)
      contentType(result) mustBe Some("text/html")
      charset(result) mustBe Some("utf-8")
    }

    "return the correct content" in {
      val result  = controller.cookies(fakeRequest)
      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "Cookies"
    }
  }
}
