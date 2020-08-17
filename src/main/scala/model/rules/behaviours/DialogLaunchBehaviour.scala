package model.rules.behaviours

import engine.events.DialogLaunchEvent
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.rules.operations.SpecialOperation.DialogOperation

private[rules] case class DialogLaunchBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(state: GameState): Seq[Operation] = {
    state.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .filter(_.isInstanceOf[DialogLaunchEvent])
      .map(_.asInstanceOf[DialogLaunchEvent])
      .consumeAll()
      .map(e => DialogOperation(e.createDialog))
  }
}
