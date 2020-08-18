package engine.events

import model.Groupable

trait GameEvent extends Serializable with Groupable {

  override def groups: Set[String] = Set()

  def name: String = this.getClass.getSimpleName //TODO CHECK

  def turn: Int

  def cycle: Int
}
