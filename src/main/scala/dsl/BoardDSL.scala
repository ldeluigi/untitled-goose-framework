package dsl

import dsl.BoardDSL.BoardState
import dsl.BoardDSL.BoardState.TilesKeyword

object BoardDSL {

  sealed trait BoardState

  object BoardState {

    sealed trait Initialized extends BoardState

    sealed trait NameDefined extends BoardState

    sealed trait BoardKeyword extends BoardState

    sealed trait NumberDefined extends BoardState

    sealed trait TilesKeyword extends BoardState

    sealed trait DispositionDefined extends BoardState

    sealed trait DispositionKeyword extends BoardState

  }

}

/*
A "goose game" board has 63 tiles in Spiral disposition {
  - 6 "the well",
  [...]
  -4,9, .. 54, 59 Group("goose")
  - 63 "The End"
 }
 */

//TODO tiles with no number??

class BoardDSL[S <: BoardState] {

  import BoardDSL._

  def A(name: String)(implicit ev: S =:= BoardState.Initialized): BoardDSL[BoardState.NameDefined] = ???

  def board(implicit ev: S =:= BoardState.NameDefined): BoardDSL[BoardState.BoardKeyword] = ???

  def has(totalTiles: Int)(implicit ev: S =:= BoardState.BoardKeyword): BoardDSL[BoardState.NumberDefined] = ???

  def tiles(implicit ev: S =:= BoardState.NumberDefined): BoardDSL[TilesKeyword] = ???

  def in(dispositionType: Any)(implicit ev: S =:= BoardState.TilesKeyword): BoardDSL[BoardState.DispositionDefined] = ???

  def disposition(implicit ev: S =:= BoardState.DispositionDefined): BoardDSL[BoardState.DispositionKeyword] = ???

}

object test {

  new BoardDSL[BoardState.Initialized].A("Ciao").board.has(63).tiles.in("Spiral").disposition
}
