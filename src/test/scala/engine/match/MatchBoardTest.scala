package engine.`match`

import model.Tile
import model.entities.board.{Board, Disposition, TileDefinition}
import org.scalatest.flatspec.AnyFlatSpec

class MatchBoardTest() extends AnyFlatSpec {

  val tile1: TileDefinition = TileDefinition(1)
  val tile2: TileDefinition = TileDefinition(2)
  val tile3: TileDefinition = TileDefinition(3)
  val tiles: Set[TileDefinition] = Set(tile1, tile2, tile3)

  val name: String = "Board"
  val board: Board = Board(name, tiles, Disposition.loop(tiles.size))
  val matchBoard: MatchBoard = MatchBoard(board)

  "A match board" should "have a board" in {
    assert(matchBoard.board == board)
  }

  it should "have tiles" in {
    assert(matchBoard.tiles.size.equals(board.tiles.size) && board.tiles.size == tiles.size)
  }

  it should "have a first tile" in {
    assert(matchBoard.first.equals(Tile(board.first)))
  }

  it should "return the next tile of a given one" in {
    assert(matchBoard.next(Tile(tile1)).get.equals(Tile(board.next(tile1).get)))
  }

  it should "return the previous tile of a given one" in {
    assert(matchBoard.prev(Tile(tile2)).get.equals(Tile(board.prev(tile2).get)))
  }

}
