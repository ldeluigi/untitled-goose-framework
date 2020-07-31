package model.actions

import engine.`match`.Match
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState

trait Action {

  def name: String

  def execute(sink: EventSink[GameEvent], state: MatchState): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

}
