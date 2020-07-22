package model

trait GameEvent {

  def name: String

  def group: List[String]

}

object GameEvent{
  private class GameEventImpl(nameEvent: String) extends GameEvent{
    override def name: String = nameEvent

    override def group: List[String] = ???
  }

  def apply(nameEvent: String): GameEvent = new GameEventImpl(nameEvent)
}

