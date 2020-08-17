package model.game

import engine.events.root.{GameEvent, PlayerEvent, TileEvent}
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

  def submitEvent(event: GameEvent): Unit
}

object Game {

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Game = new GameImpl(board, players, rules)

  private class GameImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Game {

    private val firstTurn = 0

    override val board: GameBoard = GameBoard(gameBoard)
    override val currentState: MutableGameState = MutableGameState(
      firstTurn,
      rules.first(currentState.players),
      () => rules.nextPlayer(currentState.currentPlayer, currentState.players),
      playerPieces,
      board
    )

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
