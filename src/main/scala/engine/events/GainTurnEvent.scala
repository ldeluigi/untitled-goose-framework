package engine.events

import engine.events.root.ConsumableGameEvent
import model.Player

case class GainTurnEvent(player: Player, currentTurn: Long, override var consumeTimes: Int) extends ConsumableGameEvent(currentTurn, consumeTimes){

  override def name: String = "Gain Turn Event"

}
