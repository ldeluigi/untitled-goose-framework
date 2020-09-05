package dsl.board.words.properties

import dsl.board.words.DispositionType.DispositionType

sealed trait BoardProperty

object BoardProperty {

  case class DispositionProperty(dispositionType: DispositionType) extends BoardProperty

  case class TileNumProperty(num: Int) extends BoardProperty

}
