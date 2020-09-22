package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.DispositionType.DispositionType

private[dsl] sealed trait BoardProperty

private[dsl] object BoardProperty {

  case class DispositionProperty(dispositionType: DispositionType) extends BoardProperty

  case class TileNumProperty(num: Int) extends BoardProperty

}
