package untitled.goose.framework.model.events.special

import untitled.goose.framework.model.events.GameEvent

/** A special event that exits the game simulation. */
case object ExitEvent extends GameEvent {
  override def name: String = "Exit"

  override def turn: Int = -1

  override def groups: Seq[String] = Seq()

  override def cycle: Int = -1
}
