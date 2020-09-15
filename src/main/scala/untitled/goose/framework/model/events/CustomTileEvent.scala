package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Tile

/**
 * CustomTileEvent is a CustomGameEvent that represent a generic TileEvent
 * customized by a player and that is consumed after a cycle.
 *
 * @param turn  The turn at which the event occurred.
 * @param cycle The cycle at which the event is resolved.
 * @param name  The name specified for the event.
 * @param tile  The tile that this event regarded.
 */
class CustomTileEvent(turn: Int, cycle: Int, name: String, val tile: Tile) extends CustomGameEvent(turn, cycle, name) with TileEvent

object CustomTileEvent {
  /**
   * This factory creates a CustomTileEvent based on the generic CustomGameEvent referred to a TileEvent.
   *
   * @param turn  The turn at which the event occurred.
   * @param cycle The cycle at which the event is resolved.
   * @param name  The name specified for the event.
   * @param tile  The tile that this event regarded.
   * @return A new CustomTileEvent event.
   */
  def apply(turn: Int, cycle: Int, name: String, tile: Tile): CustomTileEvent = new CustomTileEvent(turn, cycle, name, tile)
}
