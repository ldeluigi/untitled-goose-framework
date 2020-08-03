package model.actions

import engine.`match`.Match
import engine.events.DiceRollEvent
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.entities.Dice

class RollDice[DiceSide](dice: Dice[DiceSide]) extends Action {

  override def name: String = "Roll a " + dice.name + " dice"

  override def execute(sink: EventSink[GameEvent], state: MatchState): Unit = {
    sink.accept(DiceRollEvent(state.currentPlayer, dice.roll, state.currentTurn))
  }
}

object RollDice {
  def apply[DiceSide](dice: Dice[DiceSide]) = new RollDice(dice)
}
