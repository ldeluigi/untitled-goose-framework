package dsl.properties.board

import dsl.properties.board.DispositionType.DispositionType

sealed trait BoardProperty

object BoardProperty {

  case class DispositionProperty(dispositionType: DispositionType) extends BoardProperty

  case class TileNumProperty(num: Int) extends BoardProperty

}
