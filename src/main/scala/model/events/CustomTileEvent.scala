package model.events

import model.entities.runtime.Tile

case class CustomTileEvent(t: Int, c: Int, n: String, tile: Tile) extends CustomGameEvent(t, c, n) with TileEvent
