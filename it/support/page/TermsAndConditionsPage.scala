package support.page

import org.openqa.selenium.By
import support.Env

object TermsAndConditionsPage extends WebPage {
  override val url: String = Env.host + "/help/terms-and-conditions"
  override def isCurrentPage: Boolean = heading == "Terms and conditions"

  def disclaimerText: String = webDriver.findElement(By.id("disclaimer")).getText
}
