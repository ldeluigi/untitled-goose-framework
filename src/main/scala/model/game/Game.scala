package model.game

import model.Player
import model.actions.Action
import model.entities.board.{Board, Piece}
import model.rules.operations.Operation
import model.rules.ruleset.RuleSet

trait Game {

  def availableActions: Set[Action]

  def board: GameBoard

  def currentState: MutableGameState

  def stateBasedOperations: Seq[Operation]

  def cleanup: Operation
}

object Game {

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Game = new GameImpl(board, players, rules)

  private class GameImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Game {

    override val board: GameBoard = GameBoard(gameBoard)
    override val currentState: MutableGameState with CycleManager = MutableGameState(
      rules.first(playerPieces.keySet),
      () => rules.nextPlayer(currentState.currentPlayer, currentState.players),
      playerPieces,
      board
    )

    override def availableActions: Set[Action] = rules.actions(currentState)

    override def stateBasedOperations: Seq[Operation] = rules.stateBasedOperations(currentState)

    override def cleanup: Operation = {
      Operation.updateState(state => {
        currentState.consumableEvents = List()
        this.currentState.currentCycle = this.currentState.currentCycle + 1
        rules.cleanupOperations(state)
      })

    }
  }

}
