package engine.events.root

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

  def turn: Long

  def isConsumed: Boolean

  def isConsumable: Boolean

  def consume(): Unit
}




