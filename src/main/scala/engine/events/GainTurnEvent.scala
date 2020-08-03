package engine.events

import engine.events.root.ConsumableGameEvent
import model.Player

case class GainTurnEvent(player: Player, currentTurn: Long, times: Int) extends ConsumableGameEvent(currentTurn, times) {

  override def name: String = "Gain Turn Event"

}
