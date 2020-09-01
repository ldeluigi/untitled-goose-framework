package model.actions

import model.entities.Dice.MovementDice
import model.events.GameEvent
import model.events.consumable.MovementDiceRollEvent
import model.game.GameState

private class RollMovementDice(dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = "Roll " + diceNumber + " " + dice.name

  override def trigger(state: GameState): GameEvent = {
    val result = for (_ <- 0 until diceNumber) yield dice.roll
    MovementDiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, result: _*)
  }
}

object RollMovementDice {

  def apply(dice: MovementDice, diceNumber: Int = 1): Action = new RollMovementDice(dice, diceNumber)
}

