package support.page

import org.openqa.selenium.By
import support.Env

object PrivacyPage extends WebPage {
  override val url: String = "https://www.gov.uk/government/publications/data-protection-act-dpa-information-hm-revenue-and-customs-hold-about-you"
  override def isCurrentPage: Boolean = if(heading == "HMRC Privacy Notice") true else false

  // Temporary
  // override def isCurrentPage: Boolean = heading == "Privacy policy"
  //  def personalInfoText: String = webDriver.findElement(By.id("privacy-personal-info")).getText
}