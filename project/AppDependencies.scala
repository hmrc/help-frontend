import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % "7.11.0",
    "uk.gov.hmrc" %% "play-frontend-hmrc"         % "3.26.0-play-28"
  )

  val test = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"  % "7.3.0"    % Test,
    "org.scalatest"          %% "scalatest"               % "3.2.13"   % Test,
    "org.scalatestplus"      %% "selenium-4-2"            % "3.2.13.0" % "test",
    "org.jsoup"               % "jsoup"                   % "1.13.1"   % Test,
    "org.mockito"            %% "mockito-scala-scalatest" % "1.14.8"   % Test,
    "uk.gov.hmrc"            %% "webdriver-factory"       % "0.38.0"   % Test,
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.62.2"   % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0"    % Test
  )
}
