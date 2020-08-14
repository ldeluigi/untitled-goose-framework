package model.actions

import engine.core.EventSink
import engine.events.root.GameEvent
import model.`match`.MutableMatchState

trait Action {

  def name: String

  def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

}
