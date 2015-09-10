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
    "uk.gov.hmrc" %% "frontend-bootstrap" % "1.0.0",
    "uk.gov.hmrc" %% "play-config" % "1.1.0",
    "uk.gov.hmrc" %% "play-json-logger" % "1.0.0",
    "uk.gov.hmrc" %% "play-health" % "0.7.0",
    "uk.gov.hmrc" %% "govuk-template" % "3.0.0",
    "uk.gov.hmrc" %% "play-ui" % "3.0.0",
    "uk.gov.hmrc" %% "url-builder" % "0.8.0"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object IntegrationTest {
    def apply() = new TestDependencies {

      override lazy val scope = "it"

      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "2.2.2" % scope,
        "org.pegdown" % "pegdown" % "1.4.2" % scope,
        "org.jsoup" % "jsoup" % "1.7.3" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
      )
    }.test
  }

  def apply() = compile ++ IntegrationTest()
}


