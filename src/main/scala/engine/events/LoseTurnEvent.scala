package engine.events

import engine.events.root.PlayerEvent
import model.Player

case class LoseTurnEvent(player: Player, currentTurn: Long, turnLost: Int) extends PlayerEvent(player, currentTurn, turnLost) {
  override def name: String = "Lose Turn"
}
