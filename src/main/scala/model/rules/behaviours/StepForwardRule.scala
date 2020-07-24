package model.rules.behaviours

import engine.events.MovementEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepForwardOperation}

case class StepForwardRule() extends BehaviourRule {
  override def name: Option[String] = Some("Step Forward")

  override def applyRule(state: MatchState): Seq[Operation] =
    (for {
      player <- state.playerPieces.keys
      event <- player.history
      if event.isInstanceOf[MovementEvent]
      movementEvent = event.asInstanceOf[MovementEvent]
      if !movementEvent.done
    } yield {
      movementEvent.done = true
      StepForwardOperation(player)
    }).toSeq
}
