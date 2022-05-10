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

import org.jsoup.Jsoup
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class OnlineServicesTermsSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm"     -> false,
        "metrics.enabled" -> false
      )
      .build()

  "Online Services Terms and conditions page" must {
    "default language to English when there is no query parameter" in {
      val request = FakeRequest(GET, "/help/terms-and-conditions/online-services")

      val result = route(fakeApplication, request).get

      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "HMRC Online Services Terms & Conditions"
    }

    "switch language using 'lang' query parameter" in {
      val request = FakeRequest(GET, "/help/terms-and-conditions/online-services?lang=cym&foo=bar")

      val result = route(fakeApplication, request).get

      status(result) mustBe Status.SEE_OTHER
      redirectLocation(result) mustBe Some(request.path)
      val awaitResult = Await.result(result, 2 second)
      awaitResult.newCookies.find(_.name == "PLAY_LANG").map(_.value) mustBe Some("cy")
    }

    "default language to English if unknown query parameter is provided" in {
      val request = FakeRequest(GET, "/help/terms-and-conditions/online-services?xxx=cym")

      val result = route(fakeApplication, request).get

      val content = Jsoup.parse(contentAsString(result))

      val headers = content.select("h1")
      headers.size mustBe 1
      headers.first.text mustBe "HMRC Online Services Terms & Conditions"
    }
  }
}
