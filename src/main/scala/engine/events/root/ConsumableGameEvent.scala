package engine.events.root

class ConsumableGameEvent(val turn: Int, val groups: Set[String] = Set()) extends GameEvent {
  private[this] var consumed: Boolean = false

  override def isConsumed: Boolean = consumed

  override def consume(): Unit = {
    consumed = true
  }

}
