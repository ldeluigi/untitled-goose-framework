package engine.events.root

abstract class ConsumableGameEvent(currentTurn: Int) extends PersistentGameEvent(currentTurn) {
  private var consumed: Boolean = false

  override def isConsumed: Boolean = consumed

  override def consume(): Unit = {
    consumed = true
  }
}
