package engine.`match`

import model.Tile
import model.entities.board.{Board, TileDefinition}

trait MatchBoard {

  def tiles: Set[Tile]

  def board: Board

  def next(tile: Tile): Option[Tile]

  def prev(tile: Tile): Option[Tile]

  def first: Tile
}

object MatchBoard {

  def apply(board: Board): MatchBoard = new MatchBoardImpl(board)

  private class MatchBoardImpl(val board: Board) extends MatchBoard {

    private val tileMap: Map[TileDefinition, Tile] = board.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = board next tile map (tileMap(_))

    override def first: Tile = tileMap(board first)

    override def prev(tile: Tile): Option[Tile] = board prev tile map (tileMap(_))

    override def equals(obj: Any): Boolean = obj match {
      case obj: MatchBoard => obj.board.equals(board)
    }
  }

}
