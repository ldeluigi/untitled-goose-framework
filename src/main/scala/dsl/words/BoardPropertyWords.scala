package dsl.words

import dsl.board.properties.BoardProperty._
import dsl.board.properties.DispositionType

trait BoardPropertyWords {

  val board: BoardWord = BoardWord()

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def tiles(num: Int): TileNumProperty = TileNumProperty(num)

}
