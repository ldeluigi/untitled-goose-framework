package model.actions

import engine.core.EventSink
import engine.events.DiceRollEvent
import engine.events.root.GameEvent
import model.entities.Dice.Dice
import model.game.MutableGameState

/** Models a roll of a dice action. */
class RollDice[DiceSide](dice: Dice[DiceSide]) extends Action {

  override def name: String = "Roll a " + dice.name + " dice"

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    sink.accept(DiceRollEvent(state.currentPlayer, state.currentTurn, dice.roll))
  }
}

object RollDice {

  /** A factory which creates a new roll dice object. */
  def apply[DiceSide](dice: Dice[DiceSide]) = new RollDice(dice)
}
