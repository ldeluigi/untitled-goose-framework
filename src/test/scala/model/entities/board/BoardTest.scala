package model.entities.board

import org.scalatest.flatspec.AnyFlatSpec

class BoardTest extends AnyFlatSpec {

  val tile1: TileDefinition = TileDefinition(1)
  val tile2: TileDefinition = TileDefinition(2)
  val tile3: TileDefinition = TileDefinition(3)
  val tiles: Set[TileDefinition] = Set(tile1, tile2, tile3)
  val name: String = "Board"
  val disposition: Disposition = Disposition.snake(tiles.size)
  val board: Board = Board(name, tiles, disposition)

  "A Board" should "have a name" in {
    assert(board.name == name)
  }

  it should "have a disposition" in {
    assert(board.disposition == disposition)
  }

  it should "have tiles" in {
    assert(board.tiles.size == tiles.size)
  }

  it should "have a first tile" in {
    assert(board.first == tiles.head)
  }

  it can "return the next tile of a given one" in {
    assert(board.next(tile1).get.equals(tile2))
  }

  it can "return the previous tile of a given one" in {
    assert(board.prev(tile2).get.equals(tile1))
  }
}
