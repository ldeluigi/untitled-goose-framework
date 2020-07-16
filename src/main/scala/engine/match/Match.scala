package engine.`match`

import model.actions.Action
import model.{MatchState, Player, Tile}
import model.entities.board.{Board, Piece}
import model.rules.RuleSet

trait Match {

  def availableActions: Set[Action]

  def board: MatchBoard

  def players: Set[Player]

  def currentState: MatchState

  def resolveAction(action: Action): MatchState
}

object Match {

  private class MatchImpl(gameBoard: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Match {

    val board = MatchBoard(gameBoard)
    var firstTurn = 0
    for (piece <- playerPieces.values) {
      piece.setPosition(rules.startPosition(board.tiles))
    }
    var currentState: MatchState = MatchState(firstTurn, rules.first(playerPieces.keySet), playerPieces, board)

    override def players: Set[Player] = playerPieces.keySet

    override def resolveAction(action: Action): MatchState = {
      currentState = rules.resolveAction(currentState, action)
      currentState
    }

    override def availableActions: Set[Action] = rules.actions(currentState)

  }

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = new MatchImpl(board, players, rules)
}
