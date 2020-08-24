package engine.events.special

import engine.events.GameEvent

case object ExitEvent extends GameEvent {
  override def name: String = "Exit"

  override def turn: Int = -1

  override def groups: Set[String] = Set()

  override def cycle: Int = -1
}
