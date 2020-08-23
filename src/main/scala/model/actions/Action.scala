package model.actions

import engine.core.EventSink
import engine.events.root.GameEvent
import model.game.MutableGameState

/** Models a base game action */
trait Action {

  /** Sets a custon name for the action. */
  def name: String

  /** Processes a certain game event basing the changes off a mutable game state.
   *
   * @param sink  the game event
   * @param state the mutable game's state on which the changes are based off
   */
  def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

  override def toString: String = this.getClass.getName + "(" + name + ")"

}
