
import Common._

name := "inventoryMgmt"

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = project.in(file(".")).aggregate(core, inMemory, inDB)

lazy val core = project.in(file("core")).settings(commonSettings: _*)

lazy val inMemory = project.in(file("inventoryMem")).dependsOn(core).settings(commonSettings: _*)

lazy val inDB = project.in(file("InventoryDB")).dependsOn(inMemory).settings(commonSettings: _*).settings(
  name := "inventory-db",
  libraryDependencies ++= Seq("com.zaxxer" % "HikariCP" % "2.4.3",
    "com.typesafe" % "config" % "1.3.0",
    "mysql" % "mysql-connector-java" % "5.1.38"
  ))