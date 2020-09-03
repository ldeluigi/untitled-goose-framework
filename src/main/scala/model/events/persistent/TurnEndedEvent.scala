package model.events.persistent

import model.events.PlayerEvent
import model.entities.runtime.Player

case class TurnEndedEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
