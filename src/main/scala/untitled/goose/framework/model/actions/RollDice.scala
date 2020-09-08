package untitled.goose.framework.model.actions

import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.DiceRollEvent

/**
 * RollDice is an Action that represent a __generic__ dice roll.
 * This means that the dices are not necessarily six faced, with numbers,
 * but can be of any type.
 *
 * @param dice A Dice, which can be of any type of face
 *             and any number of faces, that should be rolled.
 * @tparam DiceSide The generic type of a face of the dice.
 */
class RollDice[DiceSide](actionName: String, dice: Dice[DiceSide], diceNumber: Int) extends Action {

  override def name: String = actionName

  override def trigger(state: GameState): GameEvent = {
    val result: Seq[DiceSide] = for (_ <- 0 until diceNumber) yield dice.roll
    DiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, result: _*)
  }
}

object RollDice {

  /**
   * This factory creates a RollDice action based on the generic face type.
   *
   * @param dice The dice that should be rolled.
   * @tparam DiceSide The generic type of one face of the dice.
   * @return A new RollDice action.
   */
  def apply[DiceSide](actionName: String, dice: Dice[DiceSide], diceNumber: Int) = new RollDice(actionName, dice, diceNumber)
}
