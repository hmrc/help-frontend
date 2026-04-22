import sbt.*

object AppDependencies {

  private val bootstrapVersion = "10.7.0"
  private val frontendVersion  = "12.32.1"
  private val playVersion      = "play-30"

  val compile = Seq(
    "uk.gov.hmrc" %% s"bootstrap-frontend-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc" %% s"play-frontend-hmrc-$playVersion" % frontendVersion
  )

  val test = Seq(
    "uk.gov.hmrc"         %% s"bootstrap-test-$playVersion" % bootstrapVersion % Test,
    "org.scalatestplus"   %% "mockito-3-4"                  % "3.2.10.0"       % Test,
    "org.jsoup"            % "jsoup"                        % "1.22.2"         % Test,
    "com.vladsch.flexmark" % "flexmark-all"                 % "0.64.8"         % Test
  )
}
