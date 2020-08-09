package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

case class LoseTurnEvent(source: Player, currentTurn: Int, turnLost: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {
}
