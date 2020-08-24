package model.game

import engine.events.root.{GameEvent, PlayerEvent, TileEvent}
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

  /** Submits an event to the game's history.
   *
   * @param event the event to be submitted
   */
  def submitEvent(event: GameEvent): Unit
}

object Game {

  /** A factory that created a game based on a given board, players and rules. */
  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Game = new GameImpl(board, players, rules)

  private class GameImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Game {

    private val firstTurn = 0
    private val desiredMinPlayers = 3
    private val desiredMaxPlayers = 10

    override val board: GameBoard = GameBoard(gameBoard)
    override val currentState: MutableGameState = MutableGameState(
      firstTurn,
      rules.first(playerPieces.keySet),
      () => rules.nextPlayer(currentState.currentPlayer, currentState.players),
      playerPieces,
      board
    )

    def maxMinPlayers(ruleSet: RuleSet): PlayerCardinalityRule = {
      PlayerCardinalityRule(desiredMinPlayers, desiredMaxPlayers)
    }

    override def availableActions: Set[Action] = rules.actions(currentState)

    override def stateBasedOperations: Seq[Operation] = rules.stateBasedOperations(currentState)

    override def submitEvent(event: GameEvent): Unit = {
      currentState.history = event :: currentState.history
      event match {
        case event: PlayerEvent => event.source.history = event :: event.source.history
        case event: TileEvent => event.source.history = event :: event.source.history
        case event: GameEvent => event :: this.currentState.history
      }
    }

    override def cleanup: Operation = Operation.execute(rules.cleanupOperations)
  }

}
