package main

import dsl.GooseDSL
import dsl.properties.board.DispositionType.Spiral
import scalafx.scene.paint.Color._


object DSLSandbox extends GooseDSL {

  Rules of "Goose Game"

  The game board has tiles(63)

  the game board has disposition(Spiral)

  The game board has(
    tiles(63),
    disposition(Spiral)
  )

  The tile 6 has(
    name("The well"),
    color(Red),
    background("well.png")
  )

  The tile "The well" has color(Red)

  The tiles(6, 3, 4) have group("Goose")

  All tiles "Stop Once" have color(Red)

  the tile 4 has group("goose")
}
