package engine.events.root

class PersistentGameEvent(currentTurn: Int, val groups: Set[String] = Set()) extends GameEvent {

  override def isConsumed: Boolean = false

  override def consume(): Unit = {}

  override def turn: Int = currentTurn

  override def toString: String = {
    " Turn: " + currentTurn + " " + name
  }

  override def name: String = this.getClass.getSimpleName

}
