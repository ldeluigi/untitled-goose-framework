package engine.`match`

import model.actions.Action
import model.{MatchState, Player}
import model.entities.board.{Board, Piece}
import model.rules.RuleSet

trait Match {

  def availableActions: Set[Action]

  def board: Board

  def players: Set[Player]

  def currentState: MatchState

  def resolveAction(action: Action): MatchState
}

object Match {
  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = MatchImpl(board, players, rules)
}

case class MatchImpl(board: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Match {

  var firstTurn = 0
  for (piece <- playerPieces.values) {
    piece.setPosition(rules.startPosition())
  }
  var currentState: MatchState = MatchState(firstTurn, rules.first(playerPieces.keySet), playerPieces)

  override def players: Set[Player] = playerPieces.keySet

  override def resolveAction(action: Action): MatchState = {
    currentState = rules.resolveAction(currentState, action)
    currentState
  }

  override def availableActions: Set[Action] = rules.actions(currentState)
}
