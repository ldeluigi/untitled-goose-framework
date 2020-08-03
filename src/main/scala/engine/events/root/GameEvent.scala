package engine.events.root

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

  def turn: Long

  def isConsumed: Boolean

  def isConsumable: Boolean

  def consume(): Unit

}

abstract class AbstractGameEvent(currentTurn: Long, var consumeTimes: Int) extends GameEvent {

  def consumeAll(): Unit = consumeTimes = 0

  override def isConsumable: Boolean = consumeTimes > 0

  override def isConsumed: Boolean = consumeTimes == 0

  override def consume(): Unit = {
    if (isConsumable)
      consumeTimes = consumeTimes - 1
  }

  override def group: List[String] = List()

  override def turn: Long = currentTurn
}




