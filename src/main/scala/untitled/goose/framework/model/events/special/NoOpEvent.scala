package untitled.goose.framework.model.events.special

import untitled.goose.framework.model.events.GameEvent

/** A special event that should be ignored. */
case object NoOpEvent extends GameEvent {
  override def name: String = ""

  override def turn: Int = -1

  override def groups: Set[String] = Set()

  override def cycle: Int = -1
}
