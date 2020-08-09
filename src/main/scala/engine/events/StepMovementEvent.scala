package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

case class StepMovementEvent(steps: Int, source: Player, currentTurn: Int) extends
  ConsumableGameEvent(currentTurn) with PlayerEvent {

  def movement: Int = steps

  override def toString: String = super.toString + " steps: " + steps
}

