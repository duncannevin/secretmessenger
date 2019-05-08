enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "secretmessenger"

version := "0.1"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "akkahttp-quickstart"

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

val akkaVersion = "2.5.13"
val akkaHttpVersion = "10.1.3"
val circeVersion = "0.9.3"
val akkaGuiceVersion = "3.2.0"
val mongoDriverVersion = "2.4.0"
val twirlVersion = "1.4.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,

  "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0" % Runtime,

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",

  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.sandinh" %% "akka-guice" % akkaGuiceVersion,

  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

mainClass in Global := Some(s"com.github.duncannevin.$name.Main")
