name := "untitled-goose-framework"

version := "0.1"

scalaVersion := "2.12.10"

resolvers += "Local Ivy Repository" at "file:///" + Path.userHome.absolutePath + "/.ivy2/local"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"

// Add dependency on Vert.x library
libraryDependencies += "io.vertx" %% "vertx-lang-scala" % "3.9.1"

// Add dependency on tuProlog library
libraryDependencies += "it.unibo.tuprolog" % "solve-jvm" % "0.11.2"

// Test dependencies
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"



scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions"
)


// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add dependency on JavaFX libraries, OS dependent
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

