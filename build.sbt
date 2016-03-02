import java.time.format.DateTimeFormatter
import java.time.{Clock, LocalDateTime}

lazy val repositorySettings = Seq(
  resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
  resolvers += "ReactiveCore Releases" at "http://ec2-54-236-221-63.compute-1.amazonaws.com/artifactory/libs-release",
  publishTo := Some("Artifactory Realm" at "http://ec2-54-236-221-63.compute-1.amazonaws.com/artifactory/libs-release-local"),
  credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
  publishMavenStyle := true,
  crossPaths := false
)

lazy val generalSettings = Seq(
  scalaVersion := "2.11.7",
  organization := "reactivecore",
  name := "quotes-workshop-api",
  version := s"${LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))}"
)

lazy val dependencySettings = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-server" % "2.4.6",
    "com.typesafe.play" %% "play-netty-server" % "2.4.6",
    "com.typesafe.play" %% "filters-helpers" % "2.4.6",
    "com.typesafe.play" %% "play-ws" % "2.4.6" exclude("commons-logging", "commons-logging"),
    "org.scalatest" %% "scalatest" % "2.2.5" % Test,
    "org.scalatestplus" %% "play" % "1.4.0-M4" % Test,
    "org.mockito" % "mockito-core" % "1.10.19" % Test
  ),
  dependencyOverrides += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  dependencyOverrides += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  dependencyOverrides += "com.google.guava" % "guava" % "18.0",
  dependencyOverrides += "org.apache.httpcomponents" % "httpclient" % "4.4.1",
  dependencyOverrides += "org.apache.httpcomponents" % "httpcore" % "4.4.1",
  dependencyOverrides += "commons-logging" % "commons-logging" % "1.2"
)

lazy val scoverageSettings = Seq(
  coverageExcludedPackages := "com.reactivecore.quotes.workshop.api.Bootstrap",
  coverageMinimum := 0,
  coverageFailOnMinimum := true
)

lazy val testSettings = Seq(
  fork in Test := true,
  parallelExecution in Test := false,
  test in assembly := {}
)

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

lazy val `quotes-workshop-api` = (project in file("."))
  .settings(repositorySettings: _*)
  .settings(generalSettings: _*)
  .settings(dependencySettings: _*)
  .settings(scoverageSettings: _*)
  .settings(testSettings: _*)
  .settings(Revolver.settings: _*)
  .settings(addArtifact(artifact in (Compile, assembly), assembly))
