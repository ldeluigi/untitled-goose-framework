package model.events.persistent

import model.events.PlayerEvent
import model.game.Player

case class LoseTurnEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
