package engine.events.root

class PersistentGameEvent(currentTurn: Int) extends GameEvent {

  override def isConsumed: Boolean = false

  override def consume(): Unit = {}

  override def group: List[String] = List()

  override def turn: Int = currentTurn

  override def toString: String = {
    " Turn: " + currentTurn + " " + name
  }

  override def name: String = this.getClass.getSimpleName

}
