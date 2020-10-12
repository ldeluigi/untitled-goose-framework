package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.PlayerDefinition
import untitled.goose.framework.model.events.PlayerEvent

case class VictoryEvent(player: PlayerDefinition, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent
