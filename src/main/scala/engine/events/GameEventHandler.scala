package engine.events

trait GameEventHandler[A] {

  def consume: Boolean
}
