package model.actions

import engine.`match`.Match
import engine.events.core.EventSink
import model.GameEvent

trait Action {

  def name: String

  def execute(sink: EventSink[GameEvent], state: Match): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

}
