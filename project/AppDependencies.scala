import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.22.0"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc"         % "7.28.0-play-28"
  )

  val test = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"  % bootstrapVersion % Test,
    "org.scalatest"          %% "scalatest"               % "3.2.13"         % Test,
    "org.scalatestplus"      %% "selenium-4-2"            % "3.2.13.0"       % Test,
    "org.jsoup"               % "jsoup"                   % "1.13.1"         % Test,
    "org.mockito"            %% "mockito-scala-scalatest" % "1.14.8"         % Test,
    "uk.gov.hmrc"            %% "webdriver-factory"       % "0.41.0"         % Test,
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.62.2"         % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0"          % Test
  )
}
