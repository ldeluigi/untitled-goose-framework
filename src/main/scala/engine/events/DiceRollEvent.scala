package engine.events

import engine.events.root.AbstractGameEvent

case class DiceRollEvent[DiceSide](side: DiceSide, currentTurn: Long) extends AbstractGameEvent(currentTurn) {

  override def name: String = "DiceRoll"

  override def isConsumable: Boolean = false

  def result: DiceSide = side

}
