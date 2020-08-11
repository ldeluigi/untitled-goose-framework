package engine.events.root

class PersistentGameEvent(val turn: Int, val groups: Set[String] = Set()) extends GameEvent {

  override def isConsumed: Boolean = false

  override def consume(): Unit = {}

  override def toString: String = {
    " Turn: " + turn + " " + name
  }

  override def name: String = this.getClass.getSimpleName

}
