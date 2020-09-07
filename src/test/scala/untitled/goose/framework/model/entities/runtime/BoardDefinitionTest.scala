package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.{BoardDefinition, Disposition, TileDefinition}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BoardDefinitionTest() extends AnyFlatSpec with Matchers {

  behavior of "BoardDefinitionTest"

  val tile1: TileDefinition = TileDefinition(1)
  val tile2: TileDefinition = TileDefinition(2)
  val tile3: TileDefinition = TileDefinition(3)
  val tiles: Set[TileDefinition] = Set(tile1, tile2, tile3)

  val name: String = "BoardDefinition"
  val board: BoardDefinition = BoardDefinition(name, tiles, Disposition.loop(tiles.size), tile1)
  val matchBoard: Board = Board(board)

  it should "have a definition" in {
    matchBoard.definition should equal(board)
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
