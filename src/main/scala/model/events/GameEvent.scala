package model.events

import model.Groupable

trait GameEvent extends Serializable with Groupable {

  override def groups: Set[String] = Set()

  def name: String = this.getClass.getSimpleName

  def turn: Int

  def cycle: Int

  override def toString: String = this.name + " turn: " + turn + " cycle: " + cycle

}
