import sbt.*

object AppDependencies {

  private val bootstrapVersion = "9.4.0"
  private val frontendVersion  = "10.10.0"
  private val playVersion      = "play-30"

  val compile = Seq(
    "uk.gov.hmrc" %% s"bootstrap-frontend-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc" %% s"play-frontend-hmrc-$playVersion" % frontendVersion
  )

  val test = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-$playVersion" % bootstrapVersion % Test,
    "org.scalatestplus"      %% "mockito-3-4"                  % "3.2.10.0"       % Test,
    "org.seleniumhq.selenium" % "selenium-java"                % "4.18.1"         % Test,
    "org.jsoup"               % "jsoup"                        % "1.13.1"         % Test,
    "org.scalatestplus"      %% "mockito-3-4"                  % "3.2.10.0"       % Test,
    "uk.gov.hmrc"            %% "ui-test-runner"               % "0.35.0"         % Test,
    "com.vladsch.flexmark"    % "flexmark-all"                 % "0.62.2"         % Test
  )
}
