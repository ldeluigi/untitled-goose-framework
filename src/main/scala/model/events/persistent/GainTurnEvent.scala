package model.events.persistent

import model.entities.runtime.Player
import model.events.PlayerEvent

case class GainTurnEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
