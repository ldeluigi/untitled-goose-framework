package engine.events

trait EventSink {

  def accept(event: Any): Unit
}
