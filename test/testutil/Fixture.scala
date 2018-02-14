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

package testutil


import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import org.scalatest.MustMatchers
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat

trait Fixture extends MustMatchers {

  implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()

  def view: HtmlFormat.Appendable

  lazy val html: String = view.body
  implicit lazy val doc: Document = Jsoup.parse(html)
  lazy val form: Element = doc.getElementsByTag("form").first()
  lazy val heading: Element = doc.getElementsByTag("h1").first()
  lazy val subHeading: Element = doc.getElementsByClass("heading-secondary").first()


  def findLinkByCssSelector(cssSelector: String)(implicit doc: Document): Option[Link] = {
    val fileLink: Element = doc.select(cssSelector).first()
    if (fileLink == null)
      None
    else
      Some(Link(href = fileLink.attr("href"),
        dataSso = Option(fileLink.attr("data-sso")) match {
          case Some("") => None
          case s => s
        },
        dataJourneyClick = fileLink.attr("data-journey-click"),
        text = fileLink.text(),
        rel = Option(fileLink.attr("rel")),
        target = Option(fileLink.attr("target"))))
  }

  def getElementTextById(id: String)(implicit doc: Document): String = {
    doc.getElementById(id).html()
  }


  def verifyTableRowText(tableId: String, expectedTableAnswersText: List[String], rowNumber: Int): Unit = {
    val actualTableAnswers = doc.getElementById(tableId).getElementsByTag("tr").get(rowNumber).getElementsByTag("td")

    for(rowTextNumber <- 0 until actualTableAnswers.size){

      val rowText = actualTableAnswers.get(rowTextNumber).text()

      rowText mustBe expectedTableAnswersText(rowTextNumber)

    }
  }

  def verifyTableHeadings(tableId: String, expectedTableHeadingsText: List[String]): Unit = {
    val actualTableHeadingsText = doc.getElementById(tableId).getElementsByTag("th").text()

    val actualListOfAllHeadingAnswers = actualTableHeadingsText.split(" ").toList

    actualListOfAllHeadingAnswers mustBe expectedTableHeadingsText
  }
}
