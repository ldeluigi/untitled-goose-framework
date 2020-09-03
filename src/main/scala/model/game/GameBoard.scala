package model.game

import model.entities.board.{Board, TileDefinition}

trait GameBoard {

  def tiles: Set[Tile]

  def board: Board

  def next(tile: Tile): Option[Tile]

  def prev(tile: Tile): Option[Tile]

  def first: Tile

  def ==(obj: GameBoard): Boolean = tiles == obj.tiles && board == obj.board && first == obj.first

  override def equals(obj: Any): Boolean = obj match {
    case b: GameBoard => b == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName +
    " (board:" + board + ", first: " +
    first + ", tiles: " + tiles + ")"
}

object GameBoard {

  def apply(board: Board): GameBoard = new GameBoardImpl(board)

  private class GameBoardImpl(val board: Board) extends GameBoard {

    private val tileMap: Map[TileDefinition, Tile] = board.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = board next tile.definition map (tileMap(_))

    override def first: Tile = tileMap(board first)

    override def prev(tile: Tile): Option[Tile] = board prev tile.definition map (tileMap(_))
  }

}
