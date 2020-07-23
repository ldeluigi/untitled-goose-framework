package model.actions

import engine.events.EventSink
import model.GameEvent

trait Action {

  def name: String

  def execute(sink: EventSink[GameEvent]): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

}
