package model.rules.behaviours

import engine.events.core.EventSink
import engine.events.root.GameEvent
import engine.events.{DiceRollEvent, MovementEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class MovementWithDiceRule() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(matchState: MatchState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filter(_.turn == matchState.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[DiceRollEvent[Any]]) //TODO SOLVE THIS WARNING
      .map(e => {
        e.consume()
        e.asInstanceOf[DiceRollEvent[Int]]
      })
      .map(e => moveOperation(e, matchState))
  }

  private def moveOperation(event: DiceRollEvent[Int], state: MatchState): Operation = {
    (_, e: EventSink[GameEvent]) => {
      e.accept(MovementEvent(event.result, event.player, state.currentTurn))
    }
  }

}
