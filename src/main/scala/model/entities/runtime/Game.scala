package model.entities.runtime

import model.actions.Action
import model.entities.definitions.Board
import model.rules.operations.Operation
import model.rules.ruleset.RuleSet

trait Game {

  def availableActions: Set[Action]

  /**
   * @return the runtime board
   */
  def board: GameBoard

  /**
   * @return the current runtime's state
   */
  def currentState: MutableGameState

  def stateBasedOperations: Seq[Operation]

  def cleanup: Operation

}

object Game {

  /** A factory that creates a runtime based on a given board, players and rules. */
  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Game = new GameImpl(board, players, rules)

  private class GameImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Game {

    override val board: GameBoard = GameBoard(gameBoard)
    override val currentState: CycleMutableGameState = CycleMutableGameState(
      rules.first(playerPieces.keySet),
      () => rules.nextPlayer(currentState.currentPlayer, currentState.players),
      playerPieces,
      board
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
}
