package model.entities.board

trait Piece {
  def position: Position

  def setPosition(pos: Position)
}

object Piece{
  def apply(): Piece = PieceImpl() //TODO change this
}

case class PieceImpl() extends Piece{

  override def position: Position = Position()

  override def setPosition(pos: Position): Unit = ???
}