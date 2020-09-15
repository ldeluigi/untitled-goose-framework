package untitled.goose.framework.main

import scalafx.scene.paint.Color._
import untitled.goose.framework.dsl.GooseDSL
import untitled.goose.framework.dsl.board.words.DispositionType.Spiral
import untitled.goose.framework.model.events.consumable.MovementDiceRollEvent
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType.Fixed


object GooseGame extends GooseDSL {

  Rules of "Goose Game"
  2 to 4 players

  Players have order(Fixed)

  The game board has size(63)
  the game board has disposition(Spiral)

  the tile 6 has name("The bridge")
  the tile 19 has name("The Inn")
  the tile 31 has name("The well")
  the tile 42 has name("The labyrinth")
  the tile 52 has name("The prison")
  the tile 58 has name("The death")

  the tile "The well" has background("pozzo.png")
  the tile "The Inn" has background("pozzo.png")

  The tiles(6, 19, 31, 42, 52, 58) have group("Special")
  All tiles "Special" have color(LightBlue)

  the tile 63 has name("The end")
  The tile 63 has color(Yellow)
  The tile 63 has background("oca.png")


  The tiles(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) have group("Goose")
  All tiles "Goose" have background("oca.png")


  The tiles (1 to 63) have group("field")
  All tiles "field" have color(Green)
  Players start on tile 1

  Define event "custom" having (
    "value" as[Int] value,
    )

  Define playerEvent "custom2" having (
    "asd" as[String] value
    )

  Create movementDice "six-faced" having totalSides(6)

  Players loseTurn priority is 5

  Each turn players are (
    always allowed to roll 1 movementDice "six-faced" as "roll a dice" priority 5,
    //always allowed to displayQuestion("Title", "Text", "Si" -> MakeSteps(5), "No" -> Nothing) as "Show dialog" priority 3,
    //always allowed to trigger (customGameEvent("custom") + ("value", _ => 6)) as "Something" priority 2,
    //always allowed to trigger MakeSteps(10) as "Fai 10 passi" priority 5,
    //always allowed to trigger (customPlayerEvent("custom2", _.currentPlayer) := "asd" -> "ok") as "SomethingP" priority 3
    )


  When(_ => true) and numberOf(events[MovementDiceRollEvent] matching (_ => true)) is (_ > 0) resolve (
    //trigger(customBehaviourGameEvent[MovementDiceRollEvent]("custom") + ("value", (_, e) => e.result.sum)),
    //trigger(customBehaviourPlayerEvent[MovementDiceRollEvent]("custom2", _.currentPlayer) + ("asd", (_, _) => "ok")),
    //forEach displayMessage("Event", "Working"),
    //forEach trigger ((e, s) => StepMovementEvent(e.result.sum, s.currentPlayer, s.currentTurn, s.currentCycle))
    forEach trigger ((_, s) => LoseTurn(s))
    )

  After resolving each action (
    _.currentTurn += 0
    )

  Include these system behaviours(
    MovementWithDice,
    MultipleStep,
    Teleport,
    SkipTurnManager,
    VictoryManager
  )
}

