package model.actions

import engine.core.EventSink
import engine.events.GameEvent
import model.game.MutableGameState


trait Action {

  def name: String

  def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

  override def toString: String = this.getClass.getName + "(" + name + ")"

}
