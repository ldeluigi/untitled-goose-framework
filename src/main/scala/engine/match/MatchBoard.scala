package engine.`match`

import model.Tile
import model.entities.board.{Board, Disposition}

trait MatchBoard extends Board {

  def tiles: List[Tile]

}

object MatchBoard {

  private class MatchBoardImpl(board: Board) extends MatchBoard {

    val tileList: List[Tile] = board.tiles.map(t => Tile(t))

    override def tiles: List[Tile] = tileList

    override def disposition: Disposition = board.disposition

    override def name: String = board.name
  }

  def apply(board: Board): MatchBoard = new MatchBoardImpl(board)
}
