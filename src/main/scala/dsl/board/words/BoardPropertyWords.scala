package dsl.board.words

import dsl.board.words.DispositionType.DispositionType
import dsl.board.words.properties.BoardProperty.{DispositionProperty, TileNumProperty}

trait BoardPropertyWords {

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def length(num: Int): TileNumProperty = TileNumProperty(num)

  def size(num: Int): TileNumProperty = TileNumProperty(num)

}
