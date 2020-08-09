package model.rules.behaviours

import engine.events.DialogLaunchEvent
import model.MutableMatchState
import model.rules.BehaviourRule
import model.rules.operations.{DialogOperation, Operation}

case class DialogLaunchBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(state: MutableMatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[DialogLaunchEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[DialogLaunchEvent]
      })
      .map(e => new DialogOperation(e.createDialog))
  }
}
