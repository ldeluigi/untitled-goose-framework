package untitled.goose.framework.dsl.board.words.properties

import untitled.goose.framework.dsl.board.words.DispositionType.DispositionType

sealed trait BoardProperty

object BoardProperty {

  case class DispositionProperty(dispositionType: DispositionType) extends BoardProperty

  case class TileNumProperty(num: Int) extends BoardProperty

}
