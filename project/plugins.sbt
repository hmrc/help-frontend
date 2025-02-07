resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("uk.gov.hmrc"       % "sbt-auto-build"     % "3.24.0")
addSbtPlugin("uk.gov.hmrc"       % "sbt-distributables" % "2.5.0")
addSbtPlugin("org.playframework" % "sbt-plugin"         % "3.0.5")
addSbtPlugin("com.typesafe.sbt"  % "sbt-gzip"           % "1.0.2")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"       % "2.4.0")
addSbtPlugin("uk.gov.hmrc"       % "sbt-test-report"    % "0.26.0")

//addSbtPlugin("uk.gov.hmrc" % "sbt-sass-compiler" % "0.8.0")
addSbtPlugin("io.github.irundaia" % "sbt-sassify" % "1.5.2")
