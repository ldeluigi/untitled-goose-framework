package model.entities.board

import scala.reflect.api.Position
import scala.reflect.internal.util.Position

trait Piece {
  def position: Position

  def setPosition(pos: Position)
}

object Piece{
  def apply() = MockPiece() //TODO change this
}

case class MockPiece() extends Piece{
  override def position: Position = Position
}