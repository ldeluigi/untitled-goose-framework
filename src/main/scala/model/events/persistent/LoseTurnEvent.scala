package model.events.persistent

import model.Player
import model.events.PlayerEvent

case class LoseTurnEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
