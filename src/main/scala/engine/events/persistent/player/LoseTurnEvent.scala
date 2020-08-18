package engine.events.persistent.player

import engine.events.PlayerEvent
import engine.events.persistent.PersistentGameEvent
import model.Player

case class LoseTurnEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
