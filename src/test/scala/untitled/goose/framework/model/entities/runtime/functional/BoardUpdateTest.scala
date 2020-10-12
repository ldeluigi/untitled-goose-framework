package untitled.goose.framework.model.entities.runtime.functional

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.functional.BoardUpdate.BoardUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Board, Tile}
import untitled.goose.framework.model.events.persistent.TileActivatedEvent

class BoardUpdateTest extends AnyFlatSpec with Matchers {

  behavior of "BoardUpdateTest"

  val board: Board = MatchMock.default.currentState.gameBoard
  val tile: Tile = board.tiles.values.head

  it should "updateTileHistory" in {
    val his = Seq(TileActivatedEvent(tile.definition, 1, 1))
    board.updateTileHistory(tile.definition, _ => his).tiles(tile.definition).history should equal(his)
  }

}
