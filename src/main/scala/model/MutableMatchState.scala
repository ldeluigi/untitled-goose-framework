package model

import engine.`match`.MatchBoard
import engine.events.root.GameEvent
import model.entities.board.Piece


trait MatchState {

  def newTurnStarted: Boolean

  def currentTurn: Int

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  def matchBoard: MatchBoard

  def history: List[GameEvent]
}

trait MutableMatchState extends MatchState {

  def newTurnStarted_=(value: Boolean): Unit

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Int): Unit

  def history_=(history: List[GameEvent]): Unit

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}

object MutableMatchState {

  private class MatchStateImpl(startTurn: Int, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece],
                               val matchBoard: MatchBoard) extends MutableMatchState {

    var history: List[GameEvent] = List()
    var currentTurn: Int = startTurn

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

    override def nextPlayer: Player = nextPlayerStrategy() //TODO Check if this is ok or we need a better way to do this
  }

  def apply(startTurn: Int, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: MatchBoard): MutableMatchState =
    new MatchStateImpl(startTurn, startPlayer, nextPlayerStrategy, pieces, board)
}

