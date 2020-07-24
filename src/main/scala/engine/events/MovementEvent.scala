package engine.events

import engine.events.root.PlayerEvent
import model.Player

class MovementEvent(steps: Int, player: Player) extends PlayerEvent(player) {

  def movement: Int = steps

  override def name: String = "MovementEvent"
}


object MovementEvent {

  def apply(steps: Int, player: Player): MovementEvent = new MovementEvent(steps, player)
}
