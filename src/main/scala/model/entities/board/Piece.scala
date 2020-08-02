package model.entities.board

import model.Color
import model.Color.Color

trait Piece {
  def position: Option[Position]

  def color: Color

  def setPosition(pos: Option[Position]): Piece

  def updatePosition(update: Option[Position] => Option[Position]): Piece
}

object Piece {

  private class PieceImpl(val position: Option[Position], val color: Color) extends Piece {
    override def setPosition(pos: Option[Position]): Piece = new PieceImpl(pos, color)

    override def updatePosition(update: Option[Position] => Option[Position]): Piece = setPosition(update(position))

  }

  def apply(position: Position, color: Color): Piece = {
    new PieceImpl(Some(position), color)
  }

  def apply(color: Color): Piece = {
    new PieceImpl(None, color)
  }

  def apply(): Piece = new PieceImpl(None, Color.random)
}

