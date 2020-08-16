package model.rules.behaviours

import engine.events.{LoseTurnEvent, SkipTurnEvent, StepMovementEvent}
import model.game.GameStateExtensions.PimpedHistory
import model.game.{GameState, MutableGameState}
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRule {
  override def name: Option[String] = None

  override def applyRule(state: GameState): Seq[Operation] = {
    val currentPlayerHistory = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
    var skippedTurn = 0
    if (currentPlayerHistory.exists(_.isInstanceOf[SkipTurnEvent])) {
      currentPlayerHistory
        .filter(_.isInstanceOf[SkipTurnEvent])
        .map(_.asInstanceOf[SkipTurnEvent])
        .consumeAll()
        .foreach(_ =>
          skippedTurn += 1
        )
    }
    if (skippedTurn > 0) {
      Seq(consumeTurn(skippedTurn))
    } else Seq()
  }


  def consumeTurn(toSkip: Int): Operation = {
    var skippedTurns = toSkip
    Operation.execute((state: MutableGameState) => {
      val currentPlayerHistory = state.currentPlayer.history
        .filterNotConsumed()
      if (currentPlayerHistory.exists(_.isInstanceOf[LoseTurnEvent])) {
        println("OK")
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
