import play.core.PlayVersion
import sbt._

object AppDependencies {
  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "frontend-bootstrap" % "10.5.0",
    "uk.gov.hmrc" %% "url-builder" % "2.1.0"
  )
 
  val test: Seq[ModuleID] = Seq(
        "org.mockito" % "mockito-all" % "2.0.2-beta",
        "com.typesafe.play" %% "play-test" % PlayVersion.current,
        "org.scalatest" %% "scalatest" % "2.2.6",
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1",
        "org.pegdown" % "pegdown" % "1.6.0",
        "org.jsoup" % "jsoup" % "1.8.3",
        "com.github.tomakehurst" % "wiremock" % "1.58"
      ).map(_ % "test")

  val overrideDependencies: Set[ModuleID] = Set(
    "org.seleniumhq.selenium" % "selenium-java" % "2.53.1",
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.53.1"
  ).map(_ % "test,it")

  val all: Seq[ModuleID] = compile ++ test
}
