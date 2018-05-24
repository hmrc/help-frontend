package support.page

import org.openqa.selenium.By
import support.Env

object PrivacyPage extends WebPage {
  override val url: String = Env.host + "/help/privacy"
  override def isCurrentPage: Boolean = heading == "Privacy policy"
  def personalInfoText: String = webDriver.findElement(By.id("privacy-personal-info")).getText
}