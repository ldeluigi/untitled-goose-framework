package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.PlayerDefinition

/**
 * CustomTileEvent is a CustomGameEvent that represent a generic PlayerEvent
 * customized by a player and that is consumed after a cycle.
 *
 * @param turn   The turn at which the event occurred.
 * @param cycle  The cycle at which the event is resolved.
 * @param name   The name specified for the event.
 * @param player The player that this event regarded.
 */
class CustomPlayerEvent(turn: Int, cycle: Int, name: String, val player: PlayerDefinition) extends CustomGameEvent(turn, cycle, name) with PlayerEvent

object CustomPlayerEvent {
  /**
   * This factory creates a CustomPlayerEvent based on the generic CustomGameEvent referred to a PlayerEvent.
   *
   * @param turn   The turn at which the event occurred.
   * @param cycle  The cycle at which the event is resolved.
   * @param name   The name specified for the event.
   * @param player The player that this event regarded.
   * @return A new CustomPlayerEvent event.
   */
  def apply(turn: Int, cycle: Int, name: String, player: PlayerDefinition): CustomPlayerEvent = new CustomPlayerEvent(turn, cycle, name, player)
}
