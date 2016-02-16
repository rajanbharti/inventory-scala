name := "inventoryMgmt"

version := "1.0"

scalaVersion := "2.11.7"

parallelExecution in ThisBuild := false

parallelExecution in Test := false

libraryDependencies ++= Seq("com.zaxxer" % "HikariCP" % "2.4.3",
  "com.typesafe" % "config" % "1.3.0",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test")
