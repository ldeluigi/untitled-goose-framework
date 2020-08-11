package engine.events

import engine.events.root.{PersistentGameEvent, PlayerEvent}
import model.Player

case class TurnEndedEvent(currentTurn: Int, source: Player) extends PersistentGameEvent(currentTurn) with PlayerEvent
