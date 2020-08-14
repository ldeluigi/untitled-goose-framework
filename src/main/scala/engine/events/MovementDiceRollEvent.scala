package engine.events

import model.Player

case class MovementDiceRollEvent(player: Player, t: Int, res: Int*)
  extends DiceRollEvent[Int](player, t, res: _*)