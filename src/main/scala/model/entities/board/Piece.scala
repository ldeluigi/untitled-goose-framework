package model.entities.board

import model.Color
import model.Color.Color

trait Piece {
  def position: Option[Position]

  def color: Color
}

object Piece {

  private case class PieceImpl(position: Option[Position], color: Color) extends Piece


  // TODO make decent constructors that take another Piece not this stuff
  def apply(position: Position, color: Color): Piece = {
    PieceImpl(Some(position), color)
  }

  def apply(color: Color): Piece = {
    PieceImpl(None, color)
  }

  def apply(position: Position): Piece = {
    PieceImpl(Some(position), Color.random)
  }

  def apply(): Piece = PieceImpl(None, Color.random)
}

