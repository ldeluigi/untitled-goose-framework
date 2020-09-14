name := "untitled-goose-framework"

version := "0.1"

scalaVersion := "2.12.10"

resolvers += "Local Ivy Repository" at "file:///" + Path.userHome.absolutePath + "/.ivy2/local"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"

// Test dependencies
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"

libraryDependencies += "io.vertx" %% "vertx-lang-scala" % "3.9.1"

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
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

githubWorkflowOSes := Seq("ubuntu-latest", "macos-latest", "windows-latest")

organization := "untitled.goose.framework"

githubWorkflowPublish := Seq(WorkflowStep.Use(
  "marvinpinto", "action-automatic-releases", "@latest",
  name = Some("Upload new GitHub Release"),
  params = Map(
    "repo_token" -> s"$${{ secrets.GITHUB_TOKEN }}",
    "automatic_release_tag" -> version.key.label,
    "prerelease" -> version.key.label.startsWith("0.").toString,
    "title" -> "Development Build",
    "files" -> "*.jar"
  )))
