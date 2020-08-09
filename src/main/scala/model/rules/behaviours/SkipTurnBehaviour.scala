package model.rules.behaviours

import engine.events.{LoseTurnEvent, SkipTurnEvent}
import model.MutableMatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRule {
  override def name: Option[String] = None

  override def applyRule(state: MutableMatchState): Seq[Operation] = {
    Seq(consumeTurn())
  }

  //TODO refactor it should be easier now
  def consumeTurn(): Operation = {
    Operation.execute((state: MutableMatchState) => {
      val currentPlayerHistory = state.currentPlayer.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
      if (currentPlayerHistory.exists(_.isInstanceOf[LoseTurnEvent])) {
        var skippedTurn = 0
        if (currentPlayerHistory.exists(_.isInstanceOf[SkipTurnEvent])) {
          currentPlayerHistory
            .filter(_.isInstanceOf[SkipTurnEvent])
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
    })
  }
}
