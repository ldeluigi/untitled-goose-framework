package engine.events.root

abstract class AbstractGameEvent(currentTurn: Long, var consumeTimes: Int = 1) extends GameEvent {

  def name: String = this.getClass.getSimpleName

  def consumeAll(): Unit = consumeTimes = 0

  override def isConsumable: Boolean = consumeTimes > 0

  override def isConsumed: Boolean = consumeTimes == 0

  override def consume(): Unit = {
    if (isConsumable)
      consumeTimes = consumeTimes - 1
  }

  override def group: List[String] = List()

  override def turn: Long = currentTurn

  override def toString: String = {
    " Turn: " + currentTurn + " " + name
  }
}
