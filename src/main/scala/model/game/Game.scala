package model.game

import model.Player
import model.actions.Action
import model.entities.board.{Board, Piece}
import model.rules.PlayerCardinalityRule
import model.rules.operations.Operation
import model.rules.ruleset.RuleSet

/** Models the game concept. */
trait Game {

  /**
   * @return a specific game's set of all available action
   */
  def availableActions: Set[Action]

  /**
   * @return the game board
   */
  def board: GameBoard

  /**
   * @return the current game's state
   */
  def currentState: MutableGameState

  /**
   * @return a sequence of all state based available operations.
   */
  def stateBasedOperations: Seq[Operation]

  def cleanup: Operation

}

object Game {

  /** A factory that created a game based on a given board, players and rules. */
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

    def maxMinPlayers(ruleSet: RuleSet): PlayerCardinalityRule = {
      PlayerCardinalityRule(desiredMinPlayers, desiredMaxPlayers)
    }

    override def availableActions: Set[Action] = rules.actions(currentState)

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
