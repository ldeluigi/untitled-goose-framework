package model.rules.behaviours

import engine.events.StepMovementEvent
import model.{MatchState, Player}
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepBackwardOperation, StepForwardOperation}

case class MultipleStepRule() extends BehaviourRule {

  override def name: Option[String] = Some("Multiple StepRule")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[StepMovementEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[StepMovementEvent]
      })
      .flatMap(e => generateStep(e.movement, e.sourcePlayer, e.movement >= 0))
  }

  def generateStep(step: Int, player: Player, forward: Boolean): Seq[Operation] = {
    (1 to step).toList.map(i => {
      if (forward) {
        StepForwardOperation(player, step - i)
      } else {
        StepBackwardOperation(player, step - i)
      }
    })
  }
}
