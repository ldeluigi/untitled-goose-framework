package model.entities.board

import model.{Color, Tile}
import org.scalatest.flatspec.AnyFlatSpec

class PieceTest extends AnyFlatSpec {

  val tileOne: Tile = Tile(TileDefinition(1))
  val tileTwo: Tile = Tile(TileDefinition(2))
  val piece: Piece = Piece(Color.Blue, Some(Position(tileOne)))

  "A piece" should "not have a position when created empty" in {
    val emptyPiece = Piece(Color.Blue)
    assert(emptyPiece.position.isEmpty)
  }

  it should "have a position when specified" in {
    assert(piece.position.nonEmpty)
  }

  it should "have a color" in {
    assert(piece.color != null)
    assert(piece.color == Color.Blue)
  }

  it should "set the position of the piece when given one" in {
    val position = Position(tileTwo)
    val updatedPiece = Piece(piece, Some(position))
    assert(updatedPiece.position.get.equals(position))
  }

  it should "set the color of the piece when given one" in {
    val color = Color.Red
    val updatedPiece = Piece(piece, color)
    assert(updatedPiece.color.equals(Color.Red))
  }

}
