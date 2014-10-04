import play.PlayJava

name 	:= "roadtrip-server"

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaEbean,
  cache,
  "org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "backbonejs" % "1.1.2-2",
  "org.webjars" % "underscorejs" % "1.7.0",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.2.0"  
)
