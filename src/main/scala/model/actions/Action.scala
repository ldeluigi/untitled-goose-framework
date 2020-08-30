package model.actions

import engine.events.GameEvent
import model.game.GameState


trait Action {

  def name: String

  def trigger(state: GameState): GameEvent

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

  override def toString: String = this.getClass.getName + "(" + name + ")"
}

object Action {

  private class ActionImpl(val name: String, triggerEvent: GameState => GameEvent) extends Action {
    override def trigger(state: GameState): GameEvent = triggerEvent(state)
  }

  def apply(name: String, triggerEvent: GameState => GameEvent): Action = new ActionImpl(name, triggerEvent)
}