package engine.`match`

import engine.events.root.{GameEvent, PlayerEvent, TileEvent}
import model.actions.Action
import model.entities.board.{Board, Piece}
import model.rules.operations.Operation
import model.rules.ruleset.RuleSet
import model.{MutableMatchState, Player}

trait Match {

  def availableActions: Set[Action]

  def board: MatchBoard

  def players: Set[Player]

  def currentState: MutableMatchState

  def stateBasedOperations: Seq[Operation]

  def cleanup: Operation

  def submitEvent(event: GameEvent): Unit
}

object Match {

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = new MatchImpl(board, players, rules)

  private class MatchImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Match {

    private val firstTurn = 0

    override val board: MatchBoard = MatchBoard(gameBoard)
    override val currentState: MutableMatchState = MutableMatchState(
      firstTurn,
      rules.first(playerPieces.keySet),
      () => rules.nextPlayer(currentState.currentPlayer, players),
      playerPieces,
      board
    )

    override def players: Set[Player] = playerPieces.keySet

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
