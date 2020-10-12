package untitled.goose.framework.main

import untitled.goose.framework.dsl.GooseDSL
import untitled.goose.framework.dsl.board.words.DispositionType.Spiral
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.definitions.PlayerOrderingType.Fixed
import untitled.goose.framework.model.events.CustomGameEvent


object DSLTest extends GooseDSL {

  Rules of "Goose Game"
  2 to 4 players

  Players have order(Fixed)

  The game board has size(63)
  the game board has disposition(Spiral)

  val gooseTileGroup = "GooseTile"
  val theBridge = "the Bridge"
  val theWell = "the Well"
  val theInn = "the Inn"
  val theLabyrinth = "the Labyrinth"
  val thePrison = "the Prison"
  val theDeath = "the Death"
  val theEnd = "the End"

  the tile 6 has name(theBridge)
  the tile 19 has name(theInn)
  the tile 31 has name(theWell)
  the tile 42 has name(theLabyrinth)
  the tile 52 has name(thePrison)
  the tile 58 has name(theDeath)

  The tiles (1 to 63) have group("field")
  All tiles "field" have colour(Colour("#B39DDB"))

  the tile theWell has background("pozzo.png")
  the tile theInn has background("pozzo.png")

  The tiles(6, 19, 31, 42, 52, 58) have group("Special")
  All tiles "Special" have colour(Colour.Default.Blue)

  the tile 63 has name("The end")
  The tile 63 has colour(Colour.Default.Yellow)
  The tile 63 has background("oca.png")


  The tiles(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) have group("Goose")
  All tiles "Goose" have background("oca.png")


  Players start on tile 1

  Define event "custom" having (
    "value" as[Int] value,
    )

  Define playerEvent "custom" having (
    "asd" as[String] value
    )

  Create movementDice "six-faced" having sides(1, 2, 3)

  Players loseTurn priority is 5

  Each turn players are (
    always allowed to roll 1 movementDice "six-faced" as "roll a dice" priority 5,
    always allowed to trigger (customGameEvent("custom") :+ ("value", _ => 6)) as "Something" priority 2,
    //always allowed to trigger MakeSteps(10) as "Fai 10 passi" priority 5,
    //always allowed to trigger (customPlayerEvent("custom2", _.currentPlayer) := "asd" -> "ok") as "SomethingP" priority 3
  )


  When(_ => true) and numberOf(events[CustomGameEvent] matching (_ => true)) is (n => n > 0 && n <= 1) resolve(
    //trigger(customBehaviourGameEvent[MovementDiceRollEvent]("custom") + ("value", (_, e) => e.result.sum)),
    //trigger(customBehaviourPlayerEvent[MovementDiceRollEvent]("custom2", _.currentPlayer) + ("asd", (_, _) => "ok")),
    forEach displayCustomQuestion((e, s) => ("ciao", "ciao"), ((e, s) => "ciao", gameEvent[CustomGameEvent]("custom") :+ ("value", (e, s) => 6))),
    //forEach trigger ((e, s) => StepMovementEvent(e.result.sum, s.currentPlayer, s.currentTurn, s.currentCycle))
    forEach trigger ((_, s) => LoseTurn(s))
  )

  Include these system behaviours(
    MovementWithDice,
    MultipleStep,
    Teleport,
    SkipTurnManager,
    VictoryManager
  )
}

