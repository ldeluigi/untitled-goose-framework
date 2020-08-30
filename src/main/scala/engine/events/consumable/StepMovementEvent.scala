package engine.events.consumable

import engine.events.PlayerEvent
import model.Player

case class StepMovementEvent(steps: Int, player: Player, turn: Int, cycle: Int) extends
  ConsumableGameEvent with PlayerEvent {

  def movement: Int = steps

  override def toString: String = super.toString + " steps: " + steps
}
