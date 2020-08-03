package engine.events

import engine.events.root.{AbstractGameEvent, PlayerEvent}
import model.Player

case class GainTurnEvent(player: Player, currentTurn: Long, times: Int) extends PlayerEvent(player, currentTurn, times) {

  override def name: String = "Gain Turn Event"

}
