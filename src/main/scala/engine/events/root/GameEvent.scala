package engine.events.root

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

  def turn: Int

  def isConsumed: Boolean

  def consume(): Unit
}




