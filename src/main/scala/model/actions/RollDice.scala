package model.actions

import model.entities.Dice.Dice
import model.events.GameEvent
import model.events.consumable.DiceRollEvent
import model.entities.runtime.GameState

class RollDice[DiceSide](dice: Dice[DiceSide]) extends Action {

  override def name: String = "Roll a " + dice.name + " dice"

  override def trigger(state: GameState): GameEvent = {
    DiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, dice.roll)
  }
}

object RollDice {

  def apply[DiceSide](dice: Dice[DiceSide]) = new RollDice(dice)
}
