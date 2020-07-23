package engine.events
import model.GameEvent

class MovementEvent(steps: Int) extends GameEvent {

  def movement: Int = steps

  override def name: String = "MovementEvent"

  override def group: List[String] = List()
}


object MovementEvent {

  def apply(steps: Int): MovementEvent = new MovementEvent(steps)
}
