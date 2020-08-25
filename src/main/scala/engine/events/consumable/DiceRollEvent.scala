package engine.events.consumable

import engine.events.PlayerEvent
import model.Player

class DiceRollEvent[DiceSide](val player: Player, val turn: Int, val cycle: Int, val result: DiceSide*)
  extends ConsumableGameEvent with PlayerEvent {

  override def toString: String = super.toString + " result: " + result.mkString("+")
}

object DiceRollEvent {
  def apply[DiceSide](source: Player, currentTurn: Int, cycle: Int, result: DiceSide*): DiceRollEvent[DiceSide] = new DiceRollEvent(source, currentTurn, cycle, result: _*)
}