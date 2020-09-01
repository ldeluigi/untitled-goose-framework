package model.game

import model.Player
import model.entities.board.Piece
import model.events.GameEvent
import model.events.consumable.ConsumableGameEvent

trait MutableGameState extends GameState {

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Int): Unit

  def consumableBuffer_=(events: Seq[ConsumableGameEvent]): Unit

  def gameHistory_=(events: Seq[GameEvent]): Unit

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}
