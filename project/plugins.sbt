resolvers += Resolver.bintrayIvyRepo("hmrc", "sbt-plugin-releases")
resolvers += Resolver.bintrayRepo("hmrc", "releases")
resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("uk.gov.hmrc"       % "sbt-auto-build"     % "3.0.0")
addSbtPlugin("uk.gov.hmrc"       % "sbt-distributables" % "2.1.0")
addSbtPlugin("com.typesafe.play" % "sbt-plugin"         % "2.8.7")
addSbtPlugin("com.typesafe.sbt"  % "sbt-gzip"           % "1.0.2")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"       % "2.4.0")
