import play.core.PlayVersion
import sbt._

object AppDependencies {

  //wiremock jetty dependency for 2.23.2
  val jettyVersion = "9.4.15.v20190215"
  
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
    "com.github.tomakehurst" % "wiremock" % "2.23.2",
    "org.seleniumhq.selenium" % "htmlunit-driver" % "2.35.1"
  ).map(_ % "test, it")

  val overrideDependencies: Set[ModuleID] = Set(
    "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
    
    //selenium expected conditions need a higher version of guava
    "com.google.guava" % "guava" % "23.0",

    //wiremock needs a specific version of jetty
    "org.eclipse.jetty" % "jetty-server" % jettyVersion,
    "org.eclipse.jetty" % "jetty-servlet" % jettyVersion,
    "org.eclipse.jetty" % "jetty-security" % jettyVersion,
    "org.eclipse.jetty" % "jetty-servlets" % jettyVersion,
    "org.eclipse.jetty" % "jetty-continuation" % jettyVersion,
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
    "org.eclipse.jetty" % "jetty-xml" % jettyVersion,
    "org.eclipse.jetty" % "jetty-client" % jettyVersion,
    "org.eclipse.jetty" % "jetty-http" % jettyVersion,
    "org.eclipse.jetty" % "jetty-io" % jettyVersion,
    "org.eclipse.jetty" % "jetty-util" % jettyVersion,
    "org.eclipse.jetty.websocket" % "websocket-api" % jettyVersion,
    "org.eclipse.jetty.websocket" % "websocket-common" % jettyVersion,
    "org.eclipse.jetty.websocket" % "websocket-client" % jettyVersion
  ).map(_ % "test,it")

  val all: Seq[ModuleID] = compile ++ test
}
