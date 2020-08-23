package model.entities.board

import model.Color.Color

/**
 * Model a placeholder that sits on a tile, indication where a certain player is currently located.
 **/
trait Piece {

  /**
   * @return the piece's position, if present
   */
  def position: Option[Position]

  /** The piece's color. */
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

