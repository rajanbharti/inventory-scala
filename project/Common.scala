import sbt._
import sbt.Keys._

object Common {

  lazy val commonSettings = Seq(
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.2.4" % "test")
  )
}