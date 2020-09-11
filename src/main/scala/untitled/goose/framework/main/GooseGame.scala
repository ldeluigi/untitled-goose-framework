package untitled.goose.framework.main

import scalafx.scene.paint.Color._
import untitled.goose.framework.dsl.GooseDSL
import untitled.goose.framework.dsl.board.words.DispositionType.Spiral
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

  The tiles(6, 19, 31, 42, 52, 58) have group("Special")
  All tiles "Special" have color(LightBlue)

  the tile 63 has name("The end")
  The tile 63 has color(Yellow)

  The tiles(1, 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) have group("Goose")
  All tiles "Goose" have background("oca.png")

  Players start on tile 1

  Define event "custom" having (
    "value" as[Int] value,
    )

  Create movementDice "six-faced" having totalSides(6)

  Each turn players are(
    ///always allowed to roll 1 movementDice "six-faced" as "roll a dice" priority 5,
    //always allowed to displayQuestion("Title", "Text", "Si" -> MakeSteps(5), "No" -> Nothing) as "Show dialog" priority 3,
    always allowed to trigger (customEvent("custom") := "value" -> 5) as "Something" priority 2,
    always allowed to trigger MakeSteps(10) as "Fai 10 passi" priority 5
  )

  //When(s => true) and numberOf(events[StepMovementEvent] matching (_.steps > 0)) is (_ >= 0) resolve (
  //  (e, s) => Operation.trigger(StepMovementEvent(10, s.currentPlayer, s.currentTurn, s.currentCycle))
  // )

}
