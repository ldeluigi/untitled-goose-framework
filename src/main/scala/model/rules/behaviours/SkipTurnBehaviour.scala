package model.rules.behaviours

import engine.events.{LoseTurnEvent, SkipTurnEvent}
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
    Operation.execute((state: MutableGameState) =>
      state.currentPlayer.history
        .filterNotConsumed()
        .filter(_.isInstanceOf[LoseTurnEvent])
        .take(toSkip)
        .consumeAll()
    )
  }
}
