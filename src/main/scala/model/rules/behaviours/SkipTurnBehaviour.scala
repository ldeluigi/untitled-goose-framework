package model.rules.behaviours

import engine.events.core.EventSink
import engine.events.root.{GameEvent, PlayerEvent}
import engine.events.{LoseTurnEvent, TurnSkippedEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRule {
  override def name: Option[String] = None

  override def applyRule(state: MatchState): Seq[Operation] = {
    Seq(consumeTurn())
  }

  def consumeTurn(): Operation = {
    (state: MatchState, _: EventSink[GameEvent]) => {
      val currentPlayerHistory = state.currentPlayer.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
      if (currentPlayerHistory.exists(_.isInstanceOf[LoseTurnEvent])) {
        var skippedTurn = 0
        if (currentPlayerHistory.exists(_.isInstanceOf[TurnSkippedEvent])) {
          currentPlayerHistory
            .filter(_.isInstanceOf[TurnSkippedEvent])
            .foreach(e => {
              skippedTurn += 1
              e.consume()
            })
        }
        var loseEvents = currentPlayerHistory.filter(_.isInstanceOf[LoseTurnEvent])
        while (skippedTurn > 0) {
          if (!loseEvents.head.isConsumed) {
            loseEvents.head.consume()
            skippedTurn -= 1
          } else {
            loseEvents.tail match {
              case Nil => skippedTurn = 0
              case _ => loseEvents = loseEvents.tail
            }
          }
        }
      }
    }
  }
}
