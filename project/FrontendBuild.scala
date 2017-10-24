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
    "uk.gov.hmrc" %% "frontend-bootstrap" % "8.8.0",
    "uk.gov.hmrc" %% "url-builder" % "2.1.0"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object IntegrationTest {
    def apply() = new TestDependencies {

      override lazy val scope = "test, it"

      lazy val bouncyCastleExclusionRule = ExclusionRule("org.bouncycastle", "bcprov-jdk16")


      override lazy val test = Seq(
        "org.mockito" % "mockito-all" % "2.0.2-beta" % scope,
        "uk.gov.hmrc" %% "hmrctest" % "2.4.0" excludeAll ExclusionRule(organization = "org.mockito"),
        "org.scalatest" %% "scalatest" % "2.2.6" % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % scope,
        "org.scalactic" %% "scalactic" % "2.2.6" % scope,
        "org.jsoup" % "jsoup" % "1.8.3" % scope,
        "org.pegdown" % "pegdown" % "1.6.0" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % "test",
        "com.github.tomakehurst" % "wiremock" % "1.58" % scope,
        "info.cukes" %% "cucumber-scala" % "1.2.4" % scope,
        "info.cukes" % "cucumber-junit" % "1.2.4" % scope,
        "junit" % "junit" % "4.12" % scope,
        "org.scalaj" %% "scalaj-http" % "2.3.0" % scope,
        "org.seleniumhq.selenium" % "selenium-java" % "2.53.1" % scope,

        "uk.gov.hmrc" %% "scala-webdriver" % "5.14.0" % scope excludeAll bouncyCastleExclusionRule,
        "uk.gov.hmrc" %% "frontend-bootstrap" % "7.22.0" % scope excludeAll bouncyCastleExclusionRule,
        "uk.gov.hmrc" %% "http-caching-client" % "6.0.0" % scope excludeAll bouncyCastleExclusionRule,
        "uk.gov.hmrc" %% "play-language" % "2.0.0" % scope excludeAll bouncyCastleExclusionRule,
        "uk.gov.hmrc" %% "reactivemongo-test" % "2.0.0" % scope excludeAll bouncyCastleExclusionRule
      )
    }.test
  }

  val overrideDependencies = Set(
    "org.seleniumhq.selenium" % "selenium-java" % "2.53.1" % "test,it",
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.53.1" % "test,it"
  )

  def apply() = compile ++ IntegrationTest()
}


