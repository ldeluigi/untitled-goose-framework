package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.BoardProperty.{DispositionProperty, TileNumProperty}
import untitled.goose.framework.dsl.board.words.DispositionType.DispositionType

/** Used for board properties. */
trait BoardPropertyWords {

  /** Enables "board has disposition [type]" */
  def disposition(disType: DispositionType): DispositionProperty = DispositionProperty(disType)

  /** Enables "board has length [number]". Same as size. */
  def length(num: Int): TileNumProperty = TileNumProperty(num)

  /** Enables "board has size [number]". Same as length. */
  def size(num: Int): TileNumProperty = TileNumProperty(num)

}
