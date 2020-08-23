package model.actions

import engine.core.EventSink
import engine.events.MovementDiceRollEvent
import engine.events.root.GameEvent
import model.entities.Dice.MovementDice
import model.game.MutableGameState

/** Models a roll of a dice action. */
private class RollMovementDice(dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = "Roll " + diceNumber + " " + dice.name

  /** Processes a series of roll movements.
   *
   * @param sink  the game event
   * @param state the mutable game's state on which the changes are based off
   */
  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    val result = for (i <- 0 until diceNumber) yield dice.roll
    sink.accept(MovementDiceRollEvent(state.currentPlayer, state.currentTurn, result: _*))
  }
}

object RollMovementDice {

  /** A factory which creates a new roll movement dice object. */
  def apply(dice: MovementDice, diceNumber: Int = 1): Action = new RollMovementDice(dice, diceNumber)
}

