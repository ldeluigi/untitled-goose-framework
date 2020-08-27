package dsl.words

import dsl.properties.board.BoardProperty._
import dsl.properties.board.DispositionType.DispositionType

trait BoardPropertyWords {

  val board: BoardWord = BoardWord()

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def tiles(num: Int): TileNumProperty = TileNumProperty(num)

}
