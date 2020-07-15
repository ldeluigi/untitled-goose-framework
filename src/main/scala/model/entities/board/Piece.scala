package model.entities.board

trait Piece {

  def position: Position

  def setPosition(pos: Position)
}
