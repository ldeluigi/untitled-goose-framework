package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Player

// TODO scaladoc
class CustomPlayerEvent(turn: Int, cycle: Int, name: String, val player: Player) extends CustomGameEvent(turn, cycle, name) with PlayerEvent

object CustomPlayerEvent {

  def apply(turn: Int, cycle: Int, name: String, player: Player): CustomPlayerEvent = new CustomPlayerEvent(turn, cycle, name, player)
}
