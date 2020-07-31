package model.rules.behaviours

import engine.events.TurnShouldEndEvent
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndConsumer() extends BehaviourRule {
  override def name: Option[String] = Some("TurnEndConsumer")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[TurnShouldEndEvent]) match {
      case Some(event) => Seq(consumeTurn(event, state))
      case _ => Seq()
    }
  }

  private def consumeTurn(event: GameEvent, state: MatchState): Operation = {
    event.consume()
    (s: MatchState, _: EventSink[GameEvent]) => {
      s.currentTurn = s.currentTurn + 1
      //TODO UPDATE CURRENT PLAYER WITH NEXT ONE!
    }
  }
}
