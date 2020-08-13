package engine.events.root

import model.Groupable

trait GameEvent extends Serializable with Groupable {

  def name: String = this.getClass.getSimpleName

  def turn: Int

  def isConsumed: Boolean

  def consume(): Unit
}




