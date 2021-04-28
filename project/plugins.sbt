resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2"
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)

addSbtPlugin("io.gatling" % "gatling-sbt" % "2.1.7")
addSbtPlugin("uk.gov.hmrc" % "sbt-settings" % "3.9.0")
addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "2.15.0")
