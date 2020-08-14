package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.{Player, Tile}


case class StopOnTileEvent(source: Player, tile: Tile, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent
