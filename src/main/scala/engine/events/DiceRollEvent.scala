package engine.events

import engine.events.root.{AbstractGameEvent, PlayerEvent}
import model.Player

case class DiceRollEvent[DiceSide](player: Player, side: DiceSide, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

  override def name: String = "DiceRoll value:" + side.toString

  def result: DiceSide = side

}
