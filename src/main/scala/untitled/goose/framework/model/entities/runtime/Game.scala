package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.definitions.GameDefinition
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.ruleset.{PriorityRuleSet, RuleSet}

import scala.collection.immutable.ListMap

/** A game encapsulates runtime dynamic information
 * together with static definitions of a goose-game.
 */
trait Game extends Defined[GameDefinition] {

  /** Based on current state, the available actions for the current player. */
  def availableActions: Set[Action]

  /** The game current state. */
  def currentState: GameState

  /** Based on current state and rules, the sequence of operation to execute. */
  def stateBasedOperations: (GameState, Seq[Operation])

  /** Based on current state and rules, the operation that cleans the buffers
   * at the end of a cycle.
   */
  def cleanup: Operation

}

object Game {

  private class GameImpl(playerPieces: ListMap[Player, Piece], val definition: GameDefinition) extends Game {

    val board: Board = Board(definition.board)

    val rules: RuleSet = PriorityRuleSet(
      definition.playerOrdering,
      definition.playersRange,
      definition.actionRules,
      definition.behaviourRules,
      definition.cleanupRules
    )

    private val players = playerPieces.keys.toSeq

    override val currentState: GameState = GameState(
      players,
      rules.first,
      playerPieces,
      board
    )

    override def availableActions: Set[Action] =
      if (currentState.consumableBuffer.isEmpty)
        rules.actions(currentState)
      else Set()

    override def stateBasedOperations: (GameState, Seq[Operation]) = rules.stateBasedOperations(currentState)

    override def cleanup: Operation = {
      Operation.updateState(state => {
        rules.cleanupOperations(state)
          .updateConsumableBuffer(_.filter(_.cycle > currentState.currentCycle))
          .updateCurrentCycle(_ + 1)
      })
    }
  }

  /** A factory that creates a game with a given board definition, players, pieces and rules. */
  def apply(definition: GameDefinition, players: ListMap[Player, Piece]): Game = new GameImpl(players, definition)
}
