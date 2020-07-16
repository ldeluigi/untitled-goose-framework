package engine.`match`

import model.actions.Action
import model.{MatchState, Player, Tile}
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

  private class MatchImpl(val board: Board, playerPieces: Map[Player, Piece], rules: RuleSet) extends Match {

    val tileList: List[Tile] = board.tiles.map(t => Tile(t))
    var firstTurn = 0
    for (piece <- playerPieces.values) {
      piece.setPosition(rules.startPosition(tileList))
    }
    var currentState: MatchState = MatchState(firstTurn, rules.first(playerPieces.keySet), playerPieces, tileList)

    override def players: Set[Player] = playerPieces.keySet

    override def resolveAction(action: Action): MatchState = {
      currentState = rules.resolveAction(currentState, action)
      currentState
    }

    override def availableActions: Set[Action] = rules.actions(currentState)
  }

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = new MatchImpl(board, players, rules)
}
