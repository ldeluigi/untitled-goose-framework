package model.entities.definitions

import model.Color
import model.entities.runtime.{Piece, Position, Tile}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PieceTest extends AnyFlatSpec with Matchers {

  behavior of "PieceTest"

  val tileOne: Tile = Tile(TileDefinition(1))
  val tileTwo: Tile = Tile(TileDefinition(2))
  val piece: Piece = Piece(Color.Blue, Some(Position(tileOne)))

  it should "not have a position when created empty" in {
    val emptyPiece = Piece(Color.Blue)
    emptyPiece.position.isEmpty should be(true)
  }

  it should "have a position when specified" in {
    piece.position.nonEmpty should be(true)
  }

  it should "have a color" in {
    piece.color should not be null
    piece.color should equal(Color.Blue)
  }

  it should "set the position of the piece when given one" in {
    val position = Position(tileTwo)
    val updatedPiece = Piece(piece, Some(position))
    updatedPiece.position.get should equal(position)
  }

  it should "set the color of the piece when given one" in {
    val color = Color.Red
    val updatedPiece = Piece(piece, color)
    updatedPiece.color should equal(Color.Red)
  }

}
