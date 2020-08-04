package model

import engine.`match`.MatchBoard
import engine.events.root.GameEvent
import model.entities.board.Piece


trait ReadOnlyMatchState {

  def newTurnStarted: Boolean

  def currentTurn: Long

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  def matchBoard: MatchBoard

  def history: List[GameEvent]

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}

trait MatchState extends ReadOnlyMatchState {

  def newTurnStarted_=(value: Boolean): Unit

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Long): Unit

  def history_=(history: List[GameEvent]): Unit
}

object MatchState {

  private class MatchStateImpl(startTurn: Long, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece],
                               val matchBoard: MatchBoard) extends MatchState {

    var history: List[GameEvent] = List()
    var currentTurn: Long = startTurn

    private var currentTurnPlayer: Player = startPlayer

    private var playerPiecesMap: Map[Player, Piece] = pieces

    override def updatePlayerPiece(player: Player, update: Piece => Piece): Unit =
      if (playerPiecesMap contains player) {
        playerPiecesMap += (player -> update(playerPiecesMap(player)))
      }

    override def currentPlayer: Player = currentTurnPlayer

    override def currentPlayer_=(player: Player): Unit =
      if (playerPiecesMap contains player) {
        currentTurnPlayer = player
      }

    override def playerPieces: Map[Player, Piece] = playerPiecesMap

    var newTurnStarted: Boolean = true

    override def nextPlayer: Player = nextPlayerStrategy()
  }

  def apply(startTurn: Long, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: MatchBoard): MatchState =
    new MatchStateImpl(startTurn, startPlayer, nextPlayerStrategy, pieces, board)
}

