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

package unit
package helpers

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import play.twirl.api.Html
import org.scalatest.matchers.must.Matchers
import scala.jdk.CollectionConverters._

trait JsoupHelpers extends Matchers {
  implicit class RichHtml(html: Html) {
    def select(cssQuery: String): Elements =
      parseNoPrettyPrinting(html).select(cssQuery)

    def getElementById(id: String): Element =
      parseNoPrettyPrinting(html).getElementById(id)

    def verifyTableContainsCookieName(tableId: String, cookieNames: List[String]): Unit = {
      val rows = html.getElementById(tableId).getElementsByTag("tr").asScala.toList
      cookieNames.foreach { cookieName =>
        rows.exists(_.toString.contains("<td class=\"govuk-table__cell\">" + cookieName + "</td>")) mustBe true
      }
    }

    def verifyTableHeadings(tableId: String, expectedTableHeadingsText: List[String]): Unit = {
      val actualTableHeadingsText = html.getElementById(tableId).getElementsByTag("th").text()

      val actualListOfAllHeadingAnswers = actualTableHeadingsText.split(" ").toList

      actualListOfAllHeadingAnswers mustBe expectedTableHeadingsText
    }

    def verifyTableCaption(tableId: String, expectedTableCaptionText: Option[String]): Unit = {
      val actualTableCaptionText = Option(html.getElementById(tableId).getElementsByTag("caption").text())
        .filter(_.nonEmpty)
      actualTableCaptionText mustBe expectedTableCaptionText
    }
  }

  // otherwise Jsoup inserts linefeed https://stackoverflow.com/questions/12503117/jsoup-line-feed
  def parseNoPrettyPrinting(html: Html): Document = {
    val doc = Jsoup.parse(html.body)
    doc.outputSettings().prettyPrint(false)
    doc
  }

  def asDocument(html: Html): Document = Jsoup.parse(html.toString())
}
