package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.PlayerDefinition
import untitled.goose.framework.model.events.PlayerEvent

case class StepMovementEvent(steps: Int, player: PlayerDefinition, turn: Int, cycle: Int) extends
  ConsumableGameEvent with PlayerEvent {

  def movement: Int = steps

  override def toString: String = super.toString + " steps: " + steps
}
