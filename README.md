# untitled-goose-framework
Untitled Goose Framework _(not a trademark)_ is a Scala library that is able to run the definition of a board game. It enables the creation and rules automation of games similar to the classic [Game of the Goose](https://en.wikipedia.org/wiki/Game_of_the_Goose).

## CI/CD
### Continuous Integration
A CI system tests, builds and makes new releases of the framework.
- __master__ branch:  
![Continuous Integration](https://github.com/ldeluigi/untitled-goose-framework/workflows/Continuous%20Integration/badge.svg?branch=master)
- __develop__ branch:  
![Continuous Integration](https://github.com/ldeluigi/untitled-goose-framework/workflows/Continuous%20Integration/badge.svg?branch=develop)
### Continuous Delivery
New [releases](https://github.com/ldeluigi/untitled-goose-framework/releases) are generated based on the [master](https://github.com/ldeluigi/untitled-goose-framework/tree/master) branch.  
The library is published on ~~[Github packages](https://github.com/ldeluigi?tab=packages&repo_name=untitled-goose-framework) and~~ [Sonatype](https://search.maven.org/artifact/com.github.ldeluigi/untitled-goose-framework_2.12).

## Features
- Ability to create new games with the shortest, most readable syntax possible through a Scala DSL (_Domain Specific Language_) called **GooseDSL**.
  GooseDSL is natural language oriented, in English.
- Ability to play created games locally. Given that each game must be turn-based, one single device is enough to play with any number of human players.
- Possibility to extend game classes, objects or data structures to implement more complex behaviours or functionalities. This can mean both extend the framework functionalities or customize default game logic. For example, one can reimplement the graphical components to make their own visualization.
- Possibility to extend the GooseDSL with custom shortcuts, keywords and more.

## Installation
This library works for Scala 2.12.

### Declaring the library as dependency
Note: `<version>` is the latest version available.
#### SBT (suggested)
Add in your `build.sbt` file:
```scala
libraryDependencies += "com.github.ldeluigi" % "untitled-goose-framework_2.12" % "<version>"
```
#### Gradle Kotlin DSL
Add, in your `build.gradle.kts` file, inside the `dependencies` block:
```kotlin
implementation("com.github.ldeluigi:untitled-goose-framework_2.12:<version>")
```

### Configuration for ScalaFx
#### SBT
- For __sbt__ projects you should add the following code to your `build.sbt` file, in order to enable compilation and build on every operating system:
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

## Usage
Create a class or object that extends __GooseDSL__ (package: `untitled.goose.framework.dsl`) and write your own game. Documentation on how to write a game can be found in the Wiki.

## Contribute
You can contribute to the project with [Pull Requests](https://github.com/ldeluigi/untitled-goose-framework/pulls) or [Issues](https://github.com/ldeluigi/untitled-goose-framework/issues) about bugs or feature requests.

## Examples
These are the official examples of projects that implement games using this framework:
- __[The Game of the Goose (Goose Game)](https://github.com/ldeluigi/goose-game)__
- __[Snakes and Ladders](https://github.com/ldeluigi/snakes-and-ladders)__
- __[Quiz Race](https://github.com/ldeluigi/quiz-race)__
