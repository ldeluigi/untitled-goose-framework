package model.actions

import engine.core.EventSink
import engine.events.MovementDiceRollEvent
import engine.events.root.GameEvent
import model.MutableMatchState
import model.entities.MovementDice


class RollMovementDice(dice: MovementDice) extends Action {

  override def name: String = "Roll a " + dice.name + " dice"

  override def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit = {
    sink.accept(MovementDiceRollEvent(state.currentPlayer, dice.roll, state.currentTurn))
  }
}


object RollMovementDice {
  def apply(dice: MovementDice) = new RollMovementDice(dice)
}

