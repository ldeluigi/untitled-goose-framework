package model.game

import engine.events.root.GameEvent
import model.Player
import model.entities.board.Piece

trait MutableGameState extends GameState {

  def newTurnStarted_=(value: Boolean): Unit

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Int): Unit

  def history_=(history: List[GameEvent]): Unit

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}

object MutableGameState {

  private class GameStateImpl(startTurn: Int, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece],
                              val gameBoard: GameBoard) extends MutableGameState {

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

    override def nextPlayer: Player = nextPlayerStrategy()
  }

  def apply(startTurn: Int, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: GameBoard): MutableGameState =
    new GameStateImpl(startTurn, startPlayer, nextPlayerStrategy, pieces, board)
}

