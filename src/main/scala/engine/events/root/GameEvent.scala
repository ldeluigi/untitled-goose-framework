package engine.events.root

import model.Groupable

trait GameEvent extends Serializable with Groupable {

  def name: String

  def turn: Int

  def isConsumed: Boolean

  def consume(): Unit
}




