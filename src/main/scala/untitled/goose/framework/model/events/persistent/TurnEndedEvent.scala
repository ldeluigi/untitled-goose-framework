package untitled.goose.framework.model.events.persistent

import untitled.goose.framework.model.entities.runtime.Player
import untitled.goose.framework.model.events.PlayerEvent

case class TurnEndedEvent(player: Player, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
