onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(List(
  name := "untitled-goose-framework",
  organization := "com.github.ldeluigi",
  homepage := Some(url("https://github.com/" +
    "ldeluigi" +
    "/" +
    "untitled-goose-framework")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "ldeluigi",
      "Luca Deluigi",
      "lucadelu97@gmail.com",
      url("https://github.com/ldeluigi")
    )
  ),

  githubWorkflowBuild := Seq(
    WorkflowStep.Sbt(List("test"))
  ),
  githubWorkflowPublishPreamble ++= Seq(
    WorkflowStep.Run(List(
      """VERSION=`sbt -Dsbt.ci=true 'inspect actual version' | grep "Setting: java.lang.String" | cut -d '=' -f2 | tr -d ' '`""",
      """IS_SNAPSHOT=`if [[ "${VERSION}" =~ "-" ]] ; then echo "true" ; else echo "false" ; fi`""",
      """echo "IS_SNAPSHOT=${IS_SNAPSHOT}" >> $GITHUB_ENV""",
    ),
    name = Some("Setup environment variables")),
    WorkflowStep.Use("olafurpg", "setup-gpg", "v2"),
  ),
  githubWorkflowPublish := Seq(
    WorkflowStep.Use(
      "marvinpinto", "action-automatic-releases", "latest",
      name = Some("Release to Github Releases"),
      params = Map(
        "repo_token" -> "${{ secrets.GITHUB_TOKEN }}",
        "prerelease" -> "${{ env.IS_SNAPSHOT }}",
        "title" -> "Release - Version ${{ env.VERSION }}",
        "files" -> "target/**/*.jar\ntarget/**/*.pom"
      )
    ),
    WorkflowStep.Sbt(
      List("ci-release"),
      name = Some("Release to Sonatype"),
      env = Map(
        "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
        "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
        "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
        "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
      )
    )
  ),
  githubWorkflowTargetTags ++= Seq("v*"),
  githubWorkflowPublishTargetBranches :=
    Seq(RefPredicate.StartsWith(Ref.Tag("v")))
))

ThisBuild / scalaVersion := "2.12.10"

ThisBuild / sbtVersion := "1.3.13"

//ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest", "macos-latest", "windows-latest")


ThisBuild / resolvers += "Local Ivy Repository" at "file:///" + Path.userHome.absolutePath + "/.ivy2/local"

// Add dependency on ScalaFX library
ThisBuild / libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"

// Add dependency on Vert.x library
ThisBuild / libraryDependencies += "io.vertx" %% "vertx-lang-scala" % "3.9.1"


// Test dependencies
ThisBuild / libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"


ThisBuild / scalacOptions ++= {
  Seq(
    "-language:postfixOps",
    "-language:implicitConversions"
  )
}

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add dependency on JavaFX libraries, OS dependent
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
ThisBuild / libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)
