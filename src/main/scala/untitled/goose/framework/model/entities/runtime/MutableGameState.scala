package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

trait MutableGameState extends GameState {

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Int): Unit

  def consumableBuffer_=(events: Seq[ConsumableGameEvent]): Unit

  def gameHistory_=(events: Seq[GameEvent]): Unit

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}
