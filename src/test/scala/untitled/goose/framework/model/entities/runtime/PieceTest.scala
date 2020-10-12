package untitled.goose.framework.model.entities.runtime

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.definitions.TileDefinition

class PieceTest extends AnyFlatSpec with Matchers {

  behavior of "PieceTest"

  val tileOne: Tile = Tile(TileDefinition(1))
  val tileTwo: Tile = Tile(TileDefinition(2))
  val piece: Piece = Piece(Colour.Default.Blue, Some(Position(tileOne.definition)))

  it should "not have a position when created empty" in {
    val emptyPiece = Piece(Colour.Default.Blue)
    emptyPiece.position.isEmpty should be(true)
  }

  it should "have a position when specified" in {
    piece.position.nonEmpty should be(true)
  }

  it should "have a colour" in {
    piece.colour should not be null
    piece.colour should equal(Colour.Default.Blue)
  }

  it should "set the position of the piece when given one" in {
    val position = Position(tileTwo.definition)
    val updatedPiece = Piece(piece, Some(position))
    updatedPiece.position.get should equal(position)
  }

  it should "set the colour of the piece when given one" in {
    val color = Colour.Default.Red
    val updatedPiece = Piece(piece, color)
    updatedPiece.colour should equal(Colour.Default.Red)
  }

}
