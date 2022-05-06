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

package unit

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Lang, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder

import scala.util.matching.Regex

class MessagesSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "play.i18n.langs"         -> Seq("en", "cy"),
        "enableLanguageSwitching" -> true,
        "metrics.jvm"             -> false,
        "metrics.enabled"         -> false
      )
      .build()

  implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

  val languageEnglish: Lang         = Lang.get("en").getOrElse(throw new Exception())
  val languageWelsh: Lang           = Lang.get("cy").getOrElse(throw new Exception())
  val MatchSingleQuoteOnly: Regex   = """\w+'{1}\w+""".r
  val MatchBacktickQuoteOnly: Regex = """`+""".r

  "Application" must {
    "have the correct message configs" in {
      messagesApi.messages.size mustBe 4
      messagesApi.messages.keys must contain theSameElementsAs Vector("en", "cy", "default", "default.play")
    }

    "have messages for default and cy only" in {
      messagesApi.messages("en").size mustBe 0
      val englishMessageCount = messagesApi.messages("default").size
      messagesApi.messages("cy").size mustBe englishMessageCount
      messagesApi.messages("default.play").size mustBe 44
    }
  }

  "All message files" should {
    "have the same set of keys" in {
      withClue(describeMismatch(defaultMessages.keySet, welshMessages.keySet)) {
        welshMessages.keySet mustBe defaultMessages.keySet
      }
    }
    "have a non-empty message for each key" in {
      assertNonEmptyValuesForDefaultMessages()
      assertNonEmptyValuesForWelshMessages()
    }
    "have no unescaped single quotes in value" in {
      assertCorrectUseOfQuotesForDefaultMessages()
      assertCorrectUseOfQuotesForWelshMessages()
    }
    "have a resolvable message for keys which take args" in {
      countMessagesWithArgs(welshMessages).size mustBe countMessagesWithArgs(defaultMessages).size
    }
  }

  private def countMessagesWithArgs(messages: Map[String, String]) = messages.values.filter(_.contains("{0"))

  private def assertNonEmptyValuesForDefaultMessages(): Unit =
    assertNonEmptyNonTemporaryValues("Default", defaultMessages)

  private def assertNonEmptyValuesForWelshMessages(): Unit = assertNonEmptyNonTemporaryValues("Welsh", welshMessages)

  private def assertCorrectUseOfQuotesForDefaultMessages(): Unit = assertCorrectUseOfQuotes("Default", defaultMessages)

  private def assertCorrectUseOfQuotesForWelshMessages(): Unit = assertCorrectUseOfQuotes("Welsh", welshMessages)

  private def assertNonEmptyNonTemporaryValues(label: String, messages: Map[String, String]): Unit = messages.foreach {
    case (key: String, value: String) =>
      withClue(s"In $label, there is an empty value for the key:[$key][$value]") {
        value.trim.isEmpty mustBe false
      }
  }

  private def assertCorrectUseOfQuotes(label: String, messages: Map[String, String]): Unit = messages.foreach {
    case (key: String, value: String) =>
      withClue(s"In $label, there is an unescaped or invalid quote:[$key][$value]") {
        MatchSingleQuoteOnly.findFirstIn(value).isDefined mustBe false
        MatchBacktickQuoteOnly.findFirstIn(value).isDefined mustBe false
      }
  }

  private def listMissingMessageKeys(header: String, missingKeys: Set[String]) =
    missingKeys.toList.sorted.mkString(header + displayLine, "\n", displayLine)

  private lazy val displayLine = "\n" + ("@" * 42) + "\n"

  private lazy val defaultMessages = getExpectedMessages("default") -- providedKeys

  private lazy val welshMessages = getExpectedMessages("cy") -- providedKeys

  private def getExpectedMessages(languageCode: String) =
    messagesApi.messages.getOrElse(languageCode, throw new Exception(s"Missing messages for $languageCode"))

  private def describeMismatch(defaultKeySet: Set[String], welshKeySet: Set[String]) =
    if (defaultKeySet.size > welshKeySet.size)
      listMissingMessageKeys("The following message keys are missing from the Welsh Set:", defaultKeySet -- welshKeySet)
    else
      listMissingMessageKeys(
        "The following message keys are missing from the Default Set:",
        welshKeySet -- defaultKeySet
      )

  private val providedKeys = Set("error.address.invalid.character")

}
