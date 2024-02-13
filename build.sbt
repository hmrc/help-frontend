import uk.gov.hmrc.DefaultBuildSettings.addTestReportOption

val appName = "help-frontend"

val silencerVersion = "1.7.0"

lazy val unitTestSettings =
  inConfig(Test)(Defaults.testTasks) ++
    Seq(
      Test / testOptions := Seq(Tests.Filter(_ startsWith "unit")),
      addTestReportOption(Test, "test-reports")
    )

lazy val IntegrationTest         = config("it") extend Test
lazy val integrationTestSettings =
  inConfig(IntegrationTest)(Defaults.testTasks) ++
    Seq(
      (IntegrationTest / testOptions) := Seq(Tests.Filter(_ startsWith "it")),
      addTestReportOption(IntegrationTest, "it-test-reports")
    )

lazy val AcceptanceTest         = config("acceptance") extend Test
lazy val acceptanceTestSettings =
  inConfig(AcceptanceTest)(Defaults.testTasks) ++
    Seq(
      // The following is needed to preserve the -Dbrowser option to the HMRC webdriver factory library
      AcceptanceTest / fork := false,
      (AcceptanceTest / testOptions) := Seq(Tests.Filter(_ startsWith "acceptance")),
      addTestReportOption(AcceptanceTest, "acceptance-test-reports")
    )

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .configs(AcceptanceTest, IntegrationTest)
  .settings(
    majorVersion := 4,
    PlayKeys.playDefaultPort := 9240,
    scalaVersion := "2.13.12",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.helpfrontend.config.AppConfig",
      "uk.gov.hmrc.govukfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
    ),
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s",
    Assets / pipelineStages := Seq(gzip),
    PlayKeys.devSettings ++= Seq("metrics.enabled" -> "false"),
    unitTestSettings,
    integrationTestSettings,
    acceptanceTestSettings
  )
