package engine.events.root

case object NoOpEvent extends GameEvent {
  override def name: String = ""

  override def group: List[String] = List()

  override def turn: Int = -1

  override def isConsumed: Boolean = false

  override def consume(): Unit = {}
}
