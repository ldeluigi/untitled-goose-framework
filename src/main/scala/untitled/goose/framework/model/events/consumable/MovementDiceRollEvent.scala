package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.PlayerDefinition

case class MovementDiceRollEvent(currentPlayer: PlayerDefinition, currentTurn: Int, currentCycle: Int, diceResult: Int*)
  extends DiceRollEvent[Int](currentPlayer, currentTurn, currentCycle, diceResult: _*)
