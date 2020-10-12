package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.PlayerDefinition
import untitled.goose.framework.model.events.PlayerEvent

class DiceRollEvent[DiceSide](val player: PlayerDefinition, val turn: Int, val cycle: Int, val result: DiceSide*)
  extends ConsumableGameEvent with PlayerEvent {

  override def toString: String = super.toString + " result: " + result.mkString("+")
}

object DiceRollEvent {
  def apply[DiceSide](source: PlayerDefinition, currentTurn: Int, cycle: Int, result: DiceSide*): DiceRollEvent[DiceSide] = new DiceRollEvent(source, currentTurn, cycle, result: _*)
}
