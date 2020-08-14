package model.rules.behaviours

import engine.events.DialogLaunchEvent
import model.`match`.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.rules.operations.SpecialOperation.DialogOperation

case class DialogLaunchBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[DialogLaunchEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[DialogLaunchEvent]
      })
      .map(e => DialogOperation(e.createDialog))
  }
}
