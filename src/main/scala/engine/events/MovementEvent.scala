package engine.events

import com.sun.media.jfxmedia.events.PlayerEvent

class MovementEvent() extends PlayerEvent {
  def movement: Int //TO DO
}

object MovementEvent{
  def apply(): MovementEvent = new MovementEvent()
}


