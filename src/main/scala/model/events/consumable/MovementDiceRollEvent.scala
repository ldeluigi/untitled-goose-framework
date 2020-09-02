package model.events.consumable

import model.game.Player

case class MovementDiceRollEvent(currentPlayer: Player, currentTurn: Int, currentCycle: Int, diceResult: Int*)
  extends DiceRollEvent[Int](currentPlayer, currentTurn, currentCycle, diceResult: _*)
