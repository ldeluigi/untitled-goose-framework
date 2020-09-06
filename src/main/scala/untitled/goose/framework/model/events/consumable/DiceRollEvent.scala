package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.Player
import untitled.goose.framework.model.events.PlayerEvent

class DiceRollEvent[DiceSide](val player: Player, val turn: Int, val cycle: Int, val result: DiceSide*)
  extends ConsumableGameEvent with PlayerEvent {

  override def toString: String = super.toString + " result: " + result.mkString("+")
}

object DiceRollEvent {
  def apply[DiceSide](source: Player, currentTurn: Int, cycle: Int, result: DiceSide*): DiceRollEvent[DiceSide] = new DiceRollEvent(source, currentTurn, cycle, result: _*)
}
