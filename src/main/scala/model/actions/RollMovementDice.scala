package model.actions

import model.entities.Dice.MovementDice
import model.events.GameEvent
import model.events.consumable.MovementDiceRollEvent
import model.entities.runtime.GameState

private class RollMovementDice(dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = "Roll " + diceNumber + " " + dice.name

  override def trigger(state: GameState): GameEvent = {
    val result = for (_ <- 0 until diceNumber) yield dice.roll
    MovementDiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, result: _*)
  }
}

object RollMovementDice {

  /**
   * This factory creates an Action meant for movement.
   * @param dice A dice that has integer typed faces.
   * @param diceNumber The number of dices to roll at the same time.
   * @return A new action for rolling those dices.
   */
  def apply(dice: MovementDice, diceNumber: Int = 1): Action = new RollMovementDice(dice, diceNumber)
}

