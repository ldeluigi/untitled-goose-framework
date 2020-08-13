package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

class DiceRollEvent[DiceSide](val source: Player, currentTurn: Int, val result: DiceSide*)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {

  override def toString: String = super.toString + " result: " + result.mkString("+")
}

object DiceRollEvent {
  def apply[DiceSide](source: Player, currentTurn: Int, result: DiceSide*): DiceRollEvent[DiceSide] = new DiceRollEvent(source, currentTurn, result: _*)
}
