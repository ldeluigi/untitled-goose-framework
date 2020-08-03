package model.rules.behaviours

import engine.events.TeleportEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, TeleportOperation}

class TeleportRule extends BehaviourRule{
  override def name: Option[String] = Some ("Teleport")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[TeleportEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[TeleportEvent]
      })
      .map(e => TeleportOperation(state.currentPlayer, e.tile))
  }
}
