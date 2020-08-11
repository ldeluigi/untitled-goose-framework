package model.actions

import engine.core.EventSink
import engine.events.DiceRollEvent
import engine.events.root.GameEvent
import model.MutableMatchState
import model.entities.Dice
import model.entities.Dice.Dice

class RollDice[DiceSide](dice: Dice[DiceSide]) extends Action {

  override def name: String = "Roll a " + dice.name + " dice"

  override def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit = {
    sink.accept(DiceRollEvent(state.currentPlayer, dice.roll, state.currentTurn))
  }
}

object RollDice {
  def apply[DiceSide](dice: Dice[DiceSide]) = new RollDice(dice)
}
