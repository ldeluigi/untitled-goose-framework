package model.events

import model.entities.runtime.Player
import model.events.CustomGameEvent.CustomGameEvent

case class CustomPlayerEvent(t: Int, c: Int, n: String, player: Player) extends CustomGameEvent(t, c, n) with PlayerEvent

