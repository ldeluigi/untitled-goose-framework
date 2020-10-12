package untitled.goose.framework.model.actions

import untitled.goose.framework.model.entities.Dice.MovementDice
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.MovementDiceRollEvent

private class RollMovementDice(actionName: String, dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = actionName

  override def trigger(state: GameState): GameEvent = {
    val result = for (_ <- 0 until diceNumber) yield dice.roll()
    MovementDiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, result: _*)
  }
}

object RollMovementDice {

  /**
   * This factory creates an Action meant for movement.
   *
   * @param dice       A dice that has integer typed faces.
   * @param diceNumber The number of dices to roll at the same time.
   * @return A new action for rolling those dices.
   */
  def apply(dice: MovementDice, diceNumber: Int = 1): Action =
    apply("Roll " + diceNumber + " " + dice.name, dice, diceNumber)

  /**
   * This factory creates an Action meant for movement.
   *
   * @param name       The name displayed for this action.
   * @param dice       A dice that has integer typed faces.
   * @param diceNumber The number of dices to roll at the same time.
   * @return A new action for rolling those dices.
   */
  def apply(name: String, dice: MovementDice, diceNumber: Int): Action =
    new RollMovementDice(name, dice, diceNumber)
}

