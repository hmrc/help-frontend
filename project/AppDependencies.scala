import play.core.PlayVersion
import sbt._

object AppDependencies {
  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "frontend-bootstrap" % "12.7.0",
    "uk.gov.hmrc" %% "url-builder" % "3.1.0"
  )
 
  val test: Seq[ModuleID] = Seq(
    "org.mockito" % "mockito-all" % "2.0.2-beta",
    "com.typesafe.play" %% "play-test" % PlayVersion.current,
    "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1",
    "org.pegdown" % "pegdown" % "1.6.0",
    "org.jsoup" % "jsoup" % "1.11.3",
    "com.github.tomakehurst" % "wiremock" % "2.23.2"
  ).map(_ % "test, it")

  val overrideDependencies: Set[ModuleID] = Set(
    "org.seleniumhq.selenium" % "selenium-java" % "2.53.1",
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.53.1"
  ).map(_ % "test,it")

  val all: Seq[ModuleID] = compile ++ test
}
