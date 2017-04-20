name := "akka-tutorial-client"

organization := "com.munteanu"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.munteanu" %% "scala-tutorial" % "0.0.1-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

mappings in (Compile, packageBin) ~= { _.filterNot { case (_, name) =>
  Seq("application.conf").contains(name)
}}
