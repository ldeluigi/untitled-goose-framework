package main

import dsl.GooseDSL
import dsl.board.properties.DispositionType.Spiral
import scalafx.scene.paint.Color._


object DSLSandbox extends GooseDSL {

  Rules of "Goose Game"

  The game board has tiles(63)

  The game board has(
    tiles(63),
    disposition(Spiral)
  )

  the game board has disposition(Spiral)

  The tile 6 has(
    name("The well"),
    color(Red),
    background("well.png")
  )

  The tiles(6, 3, 4) have group("Goose")

  the tile 4 has group("goose")


}
