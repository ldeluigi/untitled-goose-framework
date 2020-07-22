package engine.`match`

import engine.events.EventSink
import model.actions.Action
import model.{MatchState, Player}
import model.entities.board.{Board, Piece}
import model.rules.RuleSet

trait Match {

  def availableActions: Set[Action]

  def board: MatchBoard

  def players: Set[Player]

  def currentState: MatchState

  def resolveAction(sink: EventSink, action: Action): Unit
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

    override def resolveAction(sink: EventSink, action: Action): Unit = {
      rules.resolveAction(sink, action: Action)
    }

    override def availableActions: Set[Action] = rules.actions(currentState)

  }

  def apply(board: Board, players: Map[Player, Piece], rules: RuleSet): Match = new MatchImpl(board, players, rules)
}
