package model.entities.board

trait Piece {
  def position: Position

  def setPosition(pos: Position): Piece
}

object Piece {
  def apply(position: Position): Piece = {
    PieceImpl().setPosition(position)
  }

  def apply(): Piece = PieceImpl()
}

case class PieceImpl() extends Piece {

  var currentPosition: Option[Position] = None

  override def position: Position = currentPosition.get

  override def setPosition(pos: Position): Piece = {
    currentPosition = Some(pos)
    this
  }
}