package model.entities.board

trait Piece {
  def position: Position

  def setPosition(pos: Position): Piece
}

object Piece {

  private class PieceImpl() extends Piece {

    var currentPosition: Option[Position] = None

    override def position: Position = currentPosition.get

    override def setPosition(pos: Position): Piece = {
      currentPosition = Some(pos)
      this
    }
  }

  def apply(position: Position): Piece = {
    new PieceImpl().setPosition(position)
  }

  def apply(): Piece = new PieceImpl()
}

