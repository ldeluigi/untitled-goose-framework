package engine.`match`

import model.Tile
import model.entities.board.{Board, Disposition, TileDefinition}

trait MatchBoard {

  def tiles: List[Tile]

  def board: Board

  def next(tile: Tile): Option[Tile]
}

object MatchBoard {

  private class MatchBoardImpl(val board: Board) extends MatchBoard {

    val tileMap: Map[TileDefinition, Tile] = board.tiles map (t => t -> Tile(t)) toMap

    override def tiles: List[Tile] = tileMap.values toList

    override def next(tile: Tile): Option[Tile] = board next tile map (t => tileMap(t))
  }

  def apply(board: Board): MatchBoard = new MatchBoardImpl(board)
}
