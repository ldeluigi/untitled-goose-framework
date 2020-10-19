# untitled-goose-framework
Untitled Goose Framework _(not a trademark)_ is a Scala library that is able to run the definition of a game based on [The Game of the Goose](https://en.wikipedia.org/wiki/Game_of_the_Goose).

## CI/CD
### Continuous Integration
A CI system tests, builds and makes new releases of the framework.
- __master__ branch:  
![Continuous Integration](https://github.com/ldeluigi/untitled-goose-framework/workflows/Continuous%20Integration/badge.svg?branch=master)
- __develop__ branch:  
![Continuous Integration](https://github.com/ldeluigi/untitled-goose-framework/workflows/Continuous%20Integration/badge.svg?branch=develop)
### Continuous Delivery
New [releases](https://github.com/ldeluigi/untitled-goose-framework/releases) are generated based on the [master](https://github.com/ldeluigi/untitled-goose-framework/tree/master) branch, as well as new [Github packages](https://github.com/ldeluigi?tab=packages&repo_name=untitled-goose-framework) and Sonatype packages.

## Features
- Ability to create new games with the shortest, most readable syntax possible through a Scala DSL (_Domain Specific Language_) called **GooseDSL**.
  GooseDSL is natural language oriented, in English.
- Ability to play created games locally. Given that each game must be turn-based, one single device is enough to play with any number of human players.
- Possibility to extend game classes, objects or data structures to implement more complex behaviours or functionalities. This can mean both extend the framework functionalities or customize default game logic. For example, one can reimplement the graphical components to make their own visualization.
- Possibility to extend the GooseDSL with custom shortcuts, keywords and more.

## Installation
### Declaring the library as dependency
WIP
### Manually downloading as unmanaged jar/library
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
You can contribute to the project with [Pull Requests](https://github.com/ldeluigi/untitled-goose-framework/pulls) or [Issues](https://github.com/ldeluigi/untitled-goose-framework/issues) about bugs or feature requests.

## Examples
These are the official examples of projects that implement games using this framework:
- __[The Game of the Goose (Goose Game)](https://github.com/ldeluigi/goose-game)__
- __[Snakes and Ladders](https://github.com/ldeluigi/snakes-and-ladders)__
- __[Quiz Race](https://github.com/ldeluigi/quiz-race)__
