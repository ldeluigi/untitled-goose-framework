package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.DispositionType.DispositionType
import untitled.goose.framework.dsl.board.words.properties.BoardProperty.{DispositionProperty, TileNumProperty}

trait BoardPropertyWords {

  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  def length(num: Int): TileNumProperty = TileNumProperty(num)

  def size(num: Int): TileNumProperty = TileNumProperty(num)

}
