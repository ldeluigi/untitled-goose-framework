package model.entities.board

import model.Tile
import org.scalatest.flatspec.AnyFlatSpec

class PieceTest extends AnyFlatSpec {

  val tileOne: Tile = Tile(TileDefinition(1))
  val tileTwo: Tile = Tile(TileDefinition(2))
  val piece: Piece = Piece(Position(tileOne))

  "A piece" can "not have a position" ignore {
    val emptyPiece = Piece()
    assert(emptyPiece.position.isEmpty)
  }

  it can "have a position" ignore {
    assert(piece.position.nonEmpty)
  }

  it should "have a color" ignore {
    assert(piece.color != null)
  }

  it should "set position of the piece" ignore {
    val position = Position(tileTwo)
    assert(piece.setPosition(Some(position)).position.equals(position))
  }

  it should "update its position" ignore {
    val position = Position(tileTwo)
    def update: Option[Position] => Option[Position] = _ => Some(position)
    assert(piece.updatePosition(update).equals(position))
  }

}
