package engine.events.root

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

  def turn: Long

  def isConsumed: Boolean

  def isConsumable: Boolean

  def consume(): Unit

}

abstract class AbstractGameEvent(currentTurn: Long) extends GameEvent {

  var consumed = false

  def group: List[String] = List()

  def turn: Long = currentTurn

  def isConsumed: Boolean = consumed

  def consume(): Unit = consumed = isConsumable
}



