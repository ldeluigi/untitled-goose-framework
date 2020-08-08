package engine.events

import engine.events.root.PlayerEvent
import model.Player

case class StepMovementEvent(steps: Int, player: Player, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

  def movement: Int = steps

  override def toString: String = super.toString + " steps: " + steps
}

