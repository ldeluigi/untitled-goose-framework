package engine.events

import engine.events.root.{AbstractGameEvent, PlayerEvent}
import model.Player

case class DiceRollEvent[DiceSide](player: Player, side: DiceSide, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

  def result: DiceSide = side

  override def toString: String = super.toString + " result: " + result.toString

}
