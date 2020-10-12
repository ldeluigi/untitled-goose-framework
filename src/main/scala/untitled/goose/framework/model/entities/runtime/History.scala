package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent

trait History[E <: GameEvent] {
  def history: Seq[E]
}
