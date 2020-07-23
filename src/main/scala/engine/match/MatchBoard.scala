package engine.`match`

import model.Tile
import model.entities.board.{Board, TileDefinition}

trait MatchBoard {

  def tiles: Set[Tile]

  def board: Board

  def next(tile: Tile): Option[Tile]
}

object MatchBoard {

  private class MatchBoardImpl(val board: Board) extends MatchBoard {

    val tileMap: Map[TileDefinition, Tile] = board.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = board next tile map (t => tileMap(t))
  }

  def apply(board: Board): MatchBoard = new MatchBoardImpl(board)
}
