package engine.events

import model.Player

case class MovementDiceRollEvent(player: Player, res: Int, t: Int)
  extends DiceRollEvent[Int](player, res, t) {
  override def name: String = this.getClass.getSimpleName
}