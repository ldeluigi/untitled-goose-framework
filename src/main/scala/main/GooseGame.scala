package main

import dsl.GooseDSL
import dsl.properties.board.DispositionType.Spiral
import scalafx.scene.paint.Color._


object GooseGame extends GooseDSL {

  Rules of "Goose Game"
  2 to 4 players

  Players start on tile 1

  The game board has tiles(63)
  the game board has disposition(Spiral)

  the tile 6 has name("The bridge")
  the tile 19 has name("The Inn")
  the tile 31 has name("The well")
  the tile 42 has name("The labyrinth")
  the tile 52 has name("The prison")
  the tile 58 has name("The death")

  The tiles(6, 19, 31, 42, 52, 58) have group("Special")
  All tiles "Special" have color(LightBlue)

  the tile 63 has name("The end")
  The tile 63 has color(Yellow)

  The tiles(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) have group("Goose")
  All tiles "Goose" have color(LightGreen)


}
