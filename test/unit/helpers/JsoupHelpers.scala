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

package unit
package helpers

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import play.twirl.api.Html
import org.scalatest.matchers.must.Matchers

trait JsoupHelpers extends Matchers {
  implicit class RichHtml(html: Html) {
    def select(cssQuery: String): Elements =
      parseNoPrettyPrinting(html).select(cssQuery)

    def getElementById(id: String): Element =
      parseNoPrettyPrinting(html).getElementById(id)

    def findLinkByCssSelector(cssSelector: String): Option[Link] = {
      val fileLink: Element = html.select(cssSelector).first()
      if (fileLink == null)
        None
      else
        Some(
          Link(
            href = fileLink.attr("href"),
            dataSso = Option(fileLink.attr("data-sso")) match {
              case Some("") => None
              case s        => s
            },
            dataJourneyClick = fileLink.attr("data-journey-click"),
            text = fileLink.text(),
            rel = Option(fileLink.attr("rel")),
            target = Option(fileLink.attr("target"))
          )
        )
    }

    def verifyTableRowText(tableId: String, expectedTableAnswersText: List[String], rowNumber: Int): Unit = {
      val actualTableAnswers = html.getElementById(tableId).getElementsByTag("tr").get(rowNumber).getElementsByTag("td")

      for (rowTextNumber <- 0 until actualTableAnswers.size) {

        val rowText = actualTableAnswers.get(rowTextNumber).text()

        rowText mustBe expectedTableAnswersText(rowTextNumber)
      }
    }

    def verifyTableHeadings(tableId: String, expectedTableHeadingsText: List[String]): Unit = {
      val actualTableHeadingsText = html.getElementById(tableId).getElementsByTag("th").text()

      val actualListOfAllHeadingAnswers = actualTableHeadingsText.split(" ").toList

      actualListOfAllHeadingAnswers mustBe expectedTableHeadingsText
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
