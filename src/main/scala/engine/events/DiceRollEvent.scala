package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

case class DiceRollEvent[DiceSide](source: Player, side: DiceSide, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {

  def result: DiceSide = side

  override def toString: String = super.toString + " result: " + result.toString
}
