package untitled.goose.framework.model.events.persistent

import untitled.goose.framework.model.entities.runtime.PlayerDefinition
import untitled.goose.framework.model.events.PlayerEvent

case class LoseTurnEvent(player: PlayerDefinition, turn: Int, cycle: Int)
  extends PersistentGameEvent with PlayerEvent
