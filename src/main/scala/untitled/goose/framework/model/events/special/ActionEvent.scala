package untitled.goose.framework.model.events.special

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.events.GameEvent

/**
 * This special event tells the engine that a user selected an action.
 *
 * @param action the action chosen by the current player.
 */
case class ActionEvent(action: Action) extends GameEvent {

  override def name: String = "Action choice: " + action.name

  override def groups: Seq[String] = Seq()

  override def turn: Int = -1

  override def cycle: Int = -1
}
