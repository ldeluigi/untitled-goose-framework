package engine.events.root

class ConsumableGameEvent(currentTurn: Int) extends PersistentGameEvent(currentTurn) {
  private[this] var consumed: Boolean = false

  override def isConsumed: Boolean = consumed

  override def consume(): Unit = {
    consumed = true
  }
}
