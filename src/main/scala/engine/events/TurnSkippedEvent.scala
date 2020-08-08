package engine.events

import engine.events.root.PlayerEvent
import model.Player

case class TurnSkippedEvent(player: Player, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

}
