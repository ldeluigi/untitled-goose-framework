package engine.events

import engine.events.root.{AbstractGameEvent, PlayerEvent}
import model.Player

case class GainTurnEvent(player: Player, currentTurn: Long, turnGained: Int) extends PlayerEvent(player, currentTurn, turnGained) {

  override def name: String = "Gain Turn"

}
