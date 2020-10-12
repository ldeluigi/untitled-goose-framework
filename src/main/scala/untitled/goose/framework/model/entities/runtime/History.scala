package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent

trait History[E <: GameEvent] {
  def history: Seq[E]
}

object History {

  class LazyHistory[E <: GameEvent](historyProvider: => Seq[E]) extends History[E] {
    override def history: Seq[E] = historyProvider
  }

}
