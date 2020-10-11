package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.runtime.Board.BoardImpl
import untitled.goose.framework.model.entities.runtime.Tile.TileImpl
import untitled.goose.framework.model.entities.runtime.{Board, Tile}
import untitled.goose.framework.model.events.TileEvent

trait BoardUpdate {

  def updateTileHistory(tile: Tile, update: Seq[TileEvent] => Seq[TileEvent]): Board
}

object BoardUpdate {
  implicit def from(board: Board): BoardImpl =
    BoardImpl(board.definition,
      board.tiles,
      board.tileOrdering)

  implicit class BoardUpdateImpl(board: Board) extends BoardUpdate {
    override def updateTileHistory(tile: Tile, update: Seq[TileEvent] => Seq[TileEvent]): Board =
      board.copy(tiles = board.tiles + (tile.definition -> TileImpl(tile.definition, update(tile.history))))
  }

}
