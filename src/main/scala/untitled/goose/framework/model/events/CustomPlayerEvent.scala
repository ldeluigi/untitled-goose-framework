package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Player

case class CustomPlayerEvent(t: Int, c: Int, n: String, player: Player) extends CustomGameEvent(t, c, n) with PlayerEvent

