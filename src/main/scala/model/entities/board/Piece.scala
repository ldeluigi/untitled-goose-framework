package model.entities.board

trait Piece {
  def position: Option[Position]

  def setPosition(pos: Option[Position]): Piece

  def updatePosition(update: Option[Position] => Option[Position]): Piece
}

object Piece {

  private class PieceImpl(val position: Option[Position]) extends Piece {
    override def setPosition(pos: Option[Position]): Piece = new PieceImpl(pos)

    override def updatePosition(update: Option[Position] => Option[Position]): Piece = setPosition(update(position))
  }

  def apply(position: Position): Piece = {
    new PieceImpl(Some(position))
  }

  def apply(): Piece = new PieceImpl(None)
}

