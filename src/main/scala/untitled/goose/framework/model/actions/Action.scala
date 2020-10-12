package untitled.goose.framework.model.actions

import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

/**
 * An action is something a player can do during its turn.
 * When an action is chosen by a player the corresponding event
 * is triggered, and the engine handles it.
 */
trait Action {

  /**
   * The action name is what identifies this action,
   * and will be displayed to the player.
   *
   * @return The name, as string.
   */
  def name: String

  /**
   * The action trigger is the method that creates the event
   * that should be triggered if this action is chosen, based on
   * the current state at that time.
   *
   * @param state The state, as given by the engine.
   * @return The game event.
   */
  def trigger(state: GameState): GameEvent

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

  override def toString: String =
    this.getClass.getName + "(" + name + ")"

  override def hashCode(): Int = name.hashCode + 1
}

object Action {

  private class ActionImpl(val name: String, triggerEvent: GameState => GameEvent) extends Action {
    override def trigger(state: GameState): GameEvent = triggerEvent(state)
  }

  /**
   * This factory creates a simple Action that uses the given strategy
   * for the event creation.
   *
   * @param name            The name of the action, as string.
   * @param triggerStrategy The strategy for the event creation.
   * @return A new Action.
   */
  def apply(name: String, triggerStrategy: GameState => GameEvent): Action =
    new ActionImpl(name, triggerStrategy)
}