package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.Game.GameImpl
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.ruleset.{PriorityRuleSet, RuleSet}

trait GameUpdate {

  def updateState(update: GameState => GameState): Game


  /** Based on current state, the available actions for the current player. */
  def availableActions: Set[Action]


  /** Based on current state and rules, the sequence of operation to execute. */
  def stateBasedOperations: (GameState, Seq[Operation])

  /**
   * Based on current state and rules, the operation that cleans the buffers
   * at the end of a cycle.
   */
  def cleanup: Operation

}

object GameUpdate {

  implicit def from(game: Game): GameImpl =
    GameImpl(game.definition, game.currentState)

  implicit class GameUpdateImpl(game: Game) extends GameUpdate {
    val rules: RuleSet = PriorityRuleSet(
      game.definition.playerOrdering,
      game.definition.playersRange,
      game.definition.actionRules,
      game.definition.behaviourRules,
      game.definition.cleanupRules
    )

    override def updateState(update: GameState => GameState): Game = game.copy(currentState = update(game.currentState))

    override def stateBasedOperations: (GameState, Seq[Operation]) = rules.stateBasedOperations(game.currentState)

    override def cleanup: Operation = {
      Operation.updateState(state => {
        rules.cleanupOperations(state)
          .updateConsumableBuffer(_.filter(_.cycle > game.currentState.currentCycle))
          .updateCurrentCycle(_ + 1)
      })
    }

    override def availableActions: Set[Action] =
      if (game.currentState.consumableBuffer.isEmpty)
        rules.actions(game.currentState)
      else Set()
  }

}
