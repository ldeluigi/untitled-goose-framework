package untitled.goose.framework.model.events

import untitled.goose.framework.model.Groupable

/** An event that occurred during gameplay. */
trait GameEvent extends Serializable with Groupable {

  override def groups: Set[String] = Set()

  /** The name of the event. */
  def name: String = this.getClass.getSimpleName

  /** The turn at which the event occurred. */
  def turn: Int

  /** The cycle at which the event occurred. */
  def cycle: Int

  override def toString: String = this.name + " turn: " + turn + " cycle: " + cycle

}
