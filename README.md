# untitled-goose-framework
Untitled Goose Framework _(not a trademark)_ is a Scala library that is able to run the definition of a game based on [The Game of the Goose](https://en.wikipedia.org/wiki/Game_of_the_Goose).

## Features
- Ability to create new games with the shortest, most readable syntax possible through Scala DSL (natural language oriented, in English).
- Ability to play created games locally. Given that each game must be turn-based, one single device is needed to play.
- Possibility to extend game classes, objects or data structures to implement more complex behaviours or functionalities. This can mean both extend the framework functionalities or the game logic.
- Possibility to extend the DSL with custom shortcuts.

## Installation
1. Download the jar of the last version available in the [Release](https://github.com/ldeluigi/untitled-goose-framework/releases) section.
1. Add the jar as a dependency for your project
    - For __sbt__ projects you can simply put the jar inside a _lib_ folder in the project root.

## Usage
- Add to your project the following mandatory dependencies:
  - `org.scalafx/scalafx/14-R19` which can be found [here](https://mvnrepository.com/artifact/org.scalafx/scalafx_2.12/14-R19).
    For __sbt__ projects you should add the following code to your `build-sbt` file, in order to enable compilation and build on every operating system:
    ```scala
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
    ```
  - `io.vertx/vertx-lang-scala/3.9.1` which can be found [here](https://mvnrepository.com/artifact/io.vertx/vertx-lang-scala_2.12/3.9.1).
- Create a class or object that extends __GooseDSL__ (package: `untitled.goose.framework.dsl`) and write your own game. Documentation on how to write a game can be found in the Wiki.

## Contribute
You can contribute to the project with [Pull Requests](https://github.com/ldeluigi/untitled-goose-framework/pulls) or [Issues](https://github.com/ldeluigi/untitled-goose-framework/issues) about bugs or feature requirements.

## Examples
These are the official examples of projects that implemented games using this framework:
- __[The Game of the Goose (Goose Game)](https://github.com/ldeluigi/goose-game)__
- __[Snakes and Ladders](https://github.com/ldeluigi/snakes-and-ladders)__
- __[Quiz Race](https://github.com/ldeluigi/quiz-race)__
