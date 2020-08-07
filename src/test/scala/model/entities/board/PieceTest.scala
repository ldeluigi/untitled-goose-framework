package model.entities.board

import model.Tile
import org.scalatest.flatspec.AnyFlatSpec

class PieceTest extends AnyFlatSpec {

  val tileOne: Tile = Tile(TileDefinition(1))
  val tileTwo: Tile = Tile(TileDefinition(2))
  val piece: Piece = Piece(Position(tileOne))

  "A piece" should "not have a position when created empty" in {
    val emptyPiece = Piece()
    assert(emptyPiece.position.isEmpty)
  }

  it should "have a position when specified" in {
    assert(piece.position.nonEmpty)
  }

  it should "have a color" in {
    assert(piece.color != null)
  }

  it should "set the position of the piece when given one" in {
    val position = Position(tileTwo)
    assert(piece.setPosition(Some(position)).position.get.equals(position))
  }

  it should "update its position with the given function" in {
    val position = Position(tileTwo)

    def update: Option[Position] => Option[Position] = _ => Some(position)

    val updatedPiece = piece.updatePosition(update)
    assert(updatedPiece.position.get.equals(position))
  }

}
