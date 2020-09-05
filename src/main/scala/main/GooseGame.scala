package main

import dsl.GooseDSL
import dsl.properties.board.DispositionType.Spiral
import model.PlayerOrderingType.UserDefinedOrder
import model.events.consumable.StepMovementEvent
import scalafx.scene.paint.Color._


object GooseGame extends GooseDSL {

  Rules of "Goose Game"
  2 to 4 players

  Players have order(UserDefinedOrder)

  The game board has tiles(63)
  the game board has disposition(Spiral)

  the tile 6 has name("The bridge")
  the tile 19 has name("The Inn")
  the tile 31 has name("The well")
  the tile 42 has name("The labyrinth")
  the tile 52 has name("The prison")
  the tile 58 has name("The death")

  the tile "The well" has background("pozzo.jpg")

  The tiles(6, 19, 31, 42, 52, 58) have group("Special")
  All tiles "Special" have color(LightBlue)

  the tile 63 has name("The end")
  The tile 63 has color(Yellow)

  The tiles(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) have group("Goose")
  All tiles "Goose" have background("oca.jpg")

  Players start on tile 1

  Each turn players are (
    always allowed to trigger (s => StepMovementEvent(10, s.currentPlayer, s.currentTurn, s.currentCycle)) as "Fai 10 passi" priority 5
    )

  Define event "custom" properties[Int] "val" properties[String]("val", "ciao")

}
