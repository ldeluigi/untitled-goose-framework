package engine.events.consumable

import model.Player

case class MovementDiceRollEvent(p: Player, t: Int, c: Int, res: Int*)
  extends DiceRollEvent[Int](p, t, c, res: _*)
