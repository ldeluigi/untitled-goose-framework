package model.actions

import engine.core.EventSink
import engine.events.GameEvent
import engine.events.consumable.MovementDiceRollEvent
import model.entities.Dice.MovementDice
import model.game.MutableGameState

private class RollMovementDice(dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = "Roll " + diceNumber + " " + dice.name

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    val result = for (i <- 0 until diceNumber) yield dice.roll
    sink.accept(MovementDiceRollEvent(state.currentPlayer, state.currentTurn, state.currentCycle, result: _*))
  }
}

object RollMovementDice {

  def apply(dice: MovementDice, diceNumber: Int = 1): Action = new RollMovementDice(dice, diceNumber)
}

