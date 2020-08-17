package model.entities.board

import model.Color.Color

trait Piece {
  def position: Option[Position]

  def color: Color
}

object Piece {

  private case class PieceImpl(position: Option[Position], color: Color) extends Piece

  def apply(color: Color, position: Option[Position] = None): Piece = PieceImpl(position, color)

  def apply(piece: Piece, color: Color): Piece = PieceImpl(piece.position, color)

  def apply(piece: Piece, position: Option[Position]): Piece = PieceImpl(position, piece.color)
}

