package model.rules.behaviours

import engine.events.DialogLaunchEvent
import model.`match`.MatchState
import model.`match`.MatchStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.rules.operations.SpecialOperation.DialogOperation

case class DialogLaunchBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(implicit state: MatchState): Seq[Operation] = {
    state.history
      .filterCurrentTurn()
      .filterNotConsumed()
      .only[DialogLaunchEvent]()
      .consumeAll()
      .map(e => DialogOperation(e.createDialog))
  }
}
