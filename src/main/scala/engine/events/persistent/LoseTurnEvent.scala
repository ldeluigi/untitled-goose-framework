package engine.events.persistent

import engine.events.PlayerEvent
import model.Player

case class LoseTurnEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
