package dsl

import dsl.board.properties.BoardPropertyChanger
import dsl.words.TileWord

trait Subjects {

  val board: BoardPropertyChanger => Unit = ???

  val tile: TileWord = ???

}
