package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.definitions.BoardDefinition
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.ruleset.RuleSet

/** A game encapsulates runtime dynamic information
 *  together with static definitions of a goose-game.
 */
trait Game {

  /** Based on current state, the available actions for the current player. */
  def availableActions: Set[Action]

  /** The game current state. */
  def currentState: MutableGameState

  /** Based on current state and rules, the sequence of operation to execute. */
  def stateBasedOperations: Seq[Operation]

  /** Based on current state and rules, the operation that cleans the buffers
   * at the end of a cycle. Can also alter state.
   */
  def cleanup: Operation
}

object Game {

  private class GameImpl(gameBoard: BoardDefinition, playerPieces: Map[Player, Piece], rules: RuleSet) extends Game {

    override val currentState: CycleMutableGameState = CycleMutableGameState(
      rules.first(playerPieces.keySet),
      () => rules.nextPlayer(currentState.currentPlayer, currentState.players),
      playerPieces,
      Board(gameBoard)
    )

    override def availableActions: Set[Action] =
      if (currentState.consumableBuffer.isEmpty)
        rules.actions(currentState)
      else Set()

    override def stateBasedOperations: Seq[Operation] = rules.stateBasedOperations(currentState)

    override def cleanup: Operation = {
      Operation.updateState(state => {
        rules.cleanupOperations(state)
        currentState.consumableBuffer = currentState.consumableBuffer.filter(_.cycle > currentState.currentCycle)
        this.currentState.currentCycle = this.currentState.currentCycle + 1
      })
    }
  }

  /** A factory that creates a game with a given board definition, players, pieces and rules. */
  def apply(board: BoardDefinition, players: Map[Player, Piece], rules: RuleSet): Game = new GameImpl(board, players, rules)

}
