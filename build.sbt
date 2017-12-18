name := "akka-tutorial-client"

organization := "com.munteanu"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  "com.munteanu" %% "scala-tutorial" % "0.0.1-SNAPSHOT",
  "com.typesafe.akka" %% "akka-agent" % "2.3.6",
  "com.typesafe.akka" %% "akka-actor" % "2.3.6",
  "com.typesafe.akka" %% "akka-remote" % "2.3.6",
  "com.syncthemall" % "boilerpipe" % "1.2.2",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.specs2" %% "specs2-core" % "3.8.7" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

mappings in (Compile, packageBin) ~= { _.filterNot { case (_, name) =>
  Seq("application.conf").contains(name)
}}
