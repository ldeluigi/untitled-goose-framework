package engine.events

import engine.events.root.{PersistentGameEvent, TileEvent}
import model.Tile

case class TileActivatedEvent(source: Tile, currentTurn: Int)
  extends PersistentGameEvent(currentTurn) with TileEvent {
}

