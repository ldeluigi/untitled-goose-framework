package dsl.initializers

import dsl.properties.board.BoardProperty.{DispositionProperty, TileNumProperty}
import dsl.properties.board.DispositionType.DispositionType
import dsl.words.board.BoardWord

trait BoardPropertyWords {

  val board: BoardWord = BoardWord()

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def tiles(num: Int): TileNumProperty = TileNumProperty(num)

}
