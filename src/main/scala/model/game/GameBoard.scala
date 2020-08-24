package model.game

import model.Tile
import model.entities.board.{Board, TileDefinition}

/** Models the game's board concept. */
trait GameBoard {

  /**
   * @return the game's board tiles set
   */
  def tiles: Set[Tile]

  /**
   * @return the board itself
   */
  def board: Board

  /**
   * @param tile the tile to which the next tile is withdrawn from
   * @return the next tile
   */
  def next(tile: Tile): Option[Tile]

  /**
   * @param tile the tile to which the previous tile is withdrawn from
   * @return the previous tile
   */
  def prev(tile: Tile): Option[Tile]

  /**
   * @return the board's first tile
   */
  def first: Tile
}

object GameBoard {

  /** A factory that created a new game board. */
  def apply(board: Board): GameBoard = new GameBoardImpl(board)

  private class GameBoardImpl(val board: Board) extends GameBoard {

    private val tileMap: Map[TileDefinition, Tile] = board.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = board next tile.definition map (tileMap(_))

    override def first: Tile = tileMap(board first)

    override def prev(tile: Tile): Option[Tile] = board prev tile.definition map (tileMap(_))

    override def equals(obj: Any): Boolean = obj match {
      case obj: GameBoard => obj.board.equals(board)
    }
  }

}
