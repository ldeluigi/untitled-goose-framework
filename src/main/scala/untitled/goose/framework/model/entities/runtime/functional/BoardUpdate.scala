package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.Board
import untitled.goose.framework.model.entities.runtime.Board.BoardImpl
import untitled.goose.framework.model.entities.runtime.Tile.TileImpl
import untitled.goose.framework.model.events.TileEvent

trait BoardUpdate {

  def updateTileHistory(tile: TileDefinition, update: Seq[TileEvent] => Seq[TileEvent]): Board
}

object BoardUpdate {
  implicit def from(board: Board): BoardImpl =
    BoardImpl(board.definition,
      board.tiles,
      board.tileOrdering)

  implicit class BoardUpdateImpl(board: Board) extends BoardUpdate {
    override def updateTileHistory(tile: TileDefinition, update: Seq[TileEvent] => Seq[TileEvent]): Board =
      board.copy(tiles = board.tiles + (tile -> TileImpl(tile, update(board.tiles(tile).history))))
  }

}
