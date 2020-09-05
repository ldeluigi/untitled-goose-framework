package model.events

import model.entities.runtime.Tile
import model.events.CustomGameEvent.CustomGameEvent

case class CustomTileEvent(t: Int, c: Int, n: String, tile: Tile) extends CustomGameEvent(t, c, n) with TileEvent
