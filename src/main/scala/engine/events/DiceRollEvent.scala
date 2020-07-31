package engine.events

import engine.events.root.AbstractGameEvent

case class DiceRollEvent[DiceSide](side: DiceSide, currentTurn: Long) extends AbstractGameEvent(currentTurn) {

  override def name: String = "DiceRoll value:" + side.toString

  override def isConsumable: Boolean = true

  def result: DiceSide = side

}
