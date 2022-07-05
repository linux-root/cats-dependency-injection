ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "cats-dependency-injection"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "discipline-core" % "1.0.0",
  "org.typelevel" %% "discipline-scalatest" % "1.0.0",
  "org.typelevel" %% "cats-core" % "2.3.0",
)
