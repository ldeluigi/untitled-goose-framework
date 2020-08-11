package engine.events

import engine.events.root.{ConsumableGameEvent, TileEvent}
import model.{Player, Tile}

case class TileExitedEvent(player: Player, source: Tile, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with TileEvent
