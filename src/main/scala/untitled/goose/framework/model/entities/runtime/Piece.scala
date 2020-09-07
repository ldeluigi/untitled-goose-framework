package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.Color.Color

/** A player's piece on the board. Can be removed from the board if position is None. */
trait Piece {

  /** The current piece position on the board. */
  def position: Option[Position]

  /** This piece's color. */
  def color: Color
}

object Piece {

  private case class PieceImpl(position: Option[Position], color: Color) extends Piece

  /** A factory that creates a new piece, based on a color and an optional position. */
  def apply(color: Color, position: Option[Position] = None): Piece = PieceImpl(position, color)

  /** A factory that creates a new piece, based on a color and a piece. */
  def apply(piece: Piece, color: Color): Piece = PieceImpl(piece.position, color)

  /** A factory that creates a new piece, based on a piece and an optional position. */
  def apply(piece: Piece, position: Option[Position]): Piece = PieceImpl(position, piece.color)
}

