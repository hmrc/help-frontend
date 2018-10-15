import sbt._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin

object FrontendBuild extends Build with MicroService {

  override val appName = "help-frontend"

  override lazy val plugins: Seq[Plugins] = Seq(
    SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin
  )

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.core.PlayVersion

  val compile = Seq(
    "uk.gov.hmrc" %% "frontend-bootstrap" % "10.5.0",
    "uk.gov.hmrc" %% "url-builder" % "2.1.0"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object IntegrationTest {
    def apply() = new TestDependencies {

      override lazy val scope = "test, it"

      override lazy val test = Seq(
        "org.mockito" % "mockito-all" % "2.0.2-beta" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % "test",
        "org.scalatest" %% "scalatest" % "2.2.6" % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % scope,
        "org.pegdown" % "pegdown" % "1.6.0" % scope,
        "org.jsoup" % "jsoup" % "1.8.3" % scope,
        "com.github.tomakehurst" % "wiremock" % "1.58" % scope
      )
    }.test
  }

  val overrideDependencies = Set(
    "org.seleniumhq.selenium" % "selenium-java" % "2.53.1" % "test,it",
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.53.1" % "test,it"
  )

  def apply() = compile ++ IntegrationTest()
}


