package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

case class VictoryEvent(currentTurn: Int, source: Player)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent
