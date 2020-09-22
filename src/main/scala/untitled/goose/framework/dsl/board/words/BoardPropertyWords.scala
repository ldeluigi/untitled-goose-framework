package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.BoardProperty.{DispositionProperty, TileNumProperty}
import untitled.goose.framework.dsl.board.words.DispositionType.DispositionType

trait BoardPropertyWords {

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def length(num: Int): TileNumProperty = TileNumProperty(num)

  def size(num: Int): TileNumProperty = TileNumProperty(num)

}
