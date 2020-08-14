package model.actions

import engine.core.EventSink
import engine.events.MovementDiceRollEvent
import engine.events.root.GameEvent
import model.`match`.MutableMatchState
import model.entities.Dice.MovementDice


private class RollMovementDice(dice: MovementDice, diceNumber: Int) extends Action {

  override def name: String = "Roll " + diceNumber + " " + dice.name

  override def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit = {
    val result = for (i <- 0 until diceNumber) yield dice.roll
    sink.accept(MovementDiceRollEvent(state.currentPlayer, state.currentTurn, result: _*))
  }
}


object RollMovementDice {
  def apply(dice: MovementDice, diceNumber: Int = 1): Action = new RollMovementDice(dice, diceNumber)
}

