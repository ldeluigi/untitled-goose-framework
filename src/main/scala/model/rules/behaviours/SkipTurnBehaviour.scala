package model.rules.behaviours

import engine.events.{LoseTurnEvent, SkipTurnEvent}
import model.`match`.{MatchState, MutableMatchState}
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRule {
  override def name: Option[String] = None

  override def applyRule(state: MatchState): Seq[Operation] = {
    val currentPlayerHistory = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
    var skippedTurn = 0
    if (currentPlayerHistory.exists(_.isInstanceOf[SkipTurnEvent])) {
      currentPlayerHistory
        .filter(_.isInstanceOf[SkipTurnEvent])
        .foreach(e => {
          skippedTurn += 1
          e.consume()
        })
    }
    if (skippedTurn > 0) {
      Seq(consumeTurn(skippedTurn))
    } else Seq()
  }


  def consumeTurn(toSkip: Int): Operation = {
    var skippedTurns = toSkip
    Operation.execute((state: MutableMatchState) => {
      val currentPlayerHistory = state.currentPlayer.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
      if (currentPlayerHistory.exists(_.isInstanceOf[LoseTurnEvent])) {
        var loseEvents = currentPlayerHistory.filter(_.isInstanceOf[LoseTurnEvent])
        while (skippedTurns > 0) {
          loseEvents.head.consume()
          loseEvents.tail match {
            //TODO if you somehow skip more turns that you should have should you gain them??
            case Nil if skippedTurns > 0 => skippedTurns = 0
            case Nil => Unit
            case _ => loseEvents = loseEvents.tail
          }
        }
      }
    })
  }
}
