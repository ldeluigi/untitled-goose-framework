package engine.`match`

import model.actions.Action
import model.entities.board.{Board, Piece}
import model.rules.RuleSet
import model.rules.operations.Operation
import model.{MatchState, Player}

trait Match {

  def availableActions: Set[Action]

  def board: MatchBoard

  def players: Set[Player]

  def currentState: MatchState

  def stateBasedOperations: Seq[Operation]

}

object Match {

  private class MatchImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Match {

    val board: MatchBoard = MatchBoard(gameBoard)
    val firstTurn = 0
    for (piece <- playerPieces.values) {
      piece.setPosition(Some(rules.startPosition(board.tiles)))
    }
    val currentState: MatchState = MatchState(firstTurn, rules.first(playerPieces.keySet), playerPieces, board)

    override def players: Set[Player] = playerPieces.keySet

    override def availableActions: Set[Action] = rules.actions(currentState)

    override def stateBasedOperations: Seq[Operation] = rules.stateBasedOperations(currentState)
  }

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = new MatchImpl(board, players, rules)
}
