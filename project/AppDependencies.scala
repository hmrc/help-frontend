import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-27" % "4.0.0",
    "uk.gov.hmrc" %% "play-frontend-hmrc"         % "0.56.0-play-27"
  )

  val test = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-27"  % "4.0.0"   % Test,
    "org.scalatest"          %% "scalatest"               % "3.2.3"   % Test,
    "org.scalatestplus"      %% "selenium-3-141"          % "3.2.0.0" % Test,
    "org.jsoup"               % "jsoup"                   % "1.13.1"  % Test,
    "com.typesafe.play"      %% "play-test"               % current   % Test,
    "org.mockito"            %% "mockito-scala-scalatest" % "1.14.8"  % Test,
    "uk.gov.hmrc"            %% "webdriver-factory"       % "0.15.0"  % Test,
    "org.pegdown"             % "pegdown"                 % "1.2.1"   % Test,
    "uk.gov.hmrc"            %% "zap-automation"          % "2.8.0"   % Test,
    "com.typesafe"            % "config"                  % "1.3.2"   % Test,
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.36.8"  % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "4.0.3"   % Test
  )
}
