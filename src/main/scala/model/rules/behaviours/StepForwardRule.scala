package model.rules.behaviours

import engine.events.StepMovementEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepForwardOperation}

case class StepForwardRule() extends BehaviourRule {
  override def name: Option[String] = Some("Step Forward")

  override def applyRule(state: MatchState): Seq[Operation] =
    (for {
      player <- state.playerPieces.keys
      event <- player.history
      if event.isInstanceOf[StepMovementEvent]
      movementEvent = event.asInstanceOf[StepMovementEvent]
      if !movementEvent.isConsumed
    } yield {
      movementEvent.consume()
      StepForwardOperation(player)
    }).toSeq
}
