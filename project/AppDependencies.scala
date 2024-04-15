import sbt._

object AppDependencies {

  private val bootstrapVersion = "8.5.0"
  private val frontendVersion  = "9.6.0"
  private val playVersion      = "play-30"

  val compile = Seq(
    "uk.gov.hmrc" %% s"bootstrap-frontend-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc" %% s"play-frontend-hmrc-$playVersion" % frontendVersion
  )

  val test = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-$playVersion" % bootstrapVersion % Test,
    "org.scalatest"          %% "scalatest"                    % "3.2.17"         % Test,
    "org.scalatestplus"      %% "selenium-4-12"                % "3.2.17.0"       % Test,
    "org.jsoup"               % "jsoup"                        % "1.13.1"         % Test,
    "org.mockito"            %% "mockito-scala-scalatest"      % "1.14.8"         % Test,
    "uk.gov.hmrc"            %% "ui-test-runner"               % "0.26.0"         % Test,
    "com.vladsch.flexmark"    % "flexmark-all"                 % "0.62.2"         % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"           % "7.0.0"          % Test
  )
}
