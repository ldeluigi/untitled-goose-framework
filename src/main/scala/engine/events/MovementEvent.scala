package engine.events

import engine.events.root.PlayerEvent
import model.Player

case class MovementEvent(steps: Int, player: Player, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

  def movement: Int = steps

  override def name: String = "Movement Event " + steps

  override def isConsumable: Boolean = true

}

