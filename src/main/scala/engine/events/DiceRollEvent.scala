package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

class DiceRollEvent[DiceSide](val source: Player, val result: DiceSide, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {

  override def toString: String = super.toString + " result: " + result.toString
}

object DiceRollEvent {
  def apply[DiceSide](source: Player, result: DiceSide, currentTurn: Int): DiceRollEvent[DiceSide] = new DiceRollEvent(source, result, currentTurn)
}
