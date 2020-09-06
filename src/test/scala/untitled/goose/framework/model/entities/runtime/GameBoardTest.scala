package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.{Board, Disposition, TileDefinition}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameBoardTest() extends AnyFlatSpec with Matchers {

  behavior of "GameBoardTest"

  val tile1: TileDefinition = TileDefinition(1)
  val tile2: TileDefinition = TileDefinition(2)
  val tile3: TileDefinition = TileDefinition(3)
  val tiles: Set[TileDefinition] = Set(tile1, tile2, tile3)

  val name: String = "Board"
  val board: Board = Board(name, tiles, Disposition.loop(tiles.size))
  val matchBoard: GameBoard = GameBoard(board)

  it should "have a board" in {
    matchBoard.board should equal(board)
  }

  it should "have tiles" in {
    matchBoard.tiles.size should equal(board.tiles.size)
    board.tiles.size should equal(tiles.size)
  }

  it should "have a first tile" in {
    matchBoard.first should equal(Tile(board.first))
  }

  it should "return the next tile of a given one" in {
    matchBoard.next(Tile(tile1)).get should equal(Tile(board.next(tile1).get))
  }

  it should "return the previous tile of a given one" in {
    matchBoard.prev(Tile(tile2)).get should equal(Tile(board.prev(tile2).get))
  }

}
