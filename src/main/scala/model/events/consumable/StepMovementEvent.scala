package model.events.consumable

import model.events.PlayerEvent
import model.game.Player

case class StepMovementEvent(steps: Int, player: Player, turn: Int, cycle: Int) extends
  ConsumableGameEvent with PlayerEvent {

  def movement: Int = steps

  override def toString: String = super.toString + " steps: " + steps
}
