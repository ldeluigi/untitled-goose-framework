package model.game

import engine.events.GameEvent
import model.Player
import model.entities.board.Piece

trait MutableGameState extends GameState {

  def currentPlayer_=(player: Player): Unit

  def currentTurn_=(turn: Int): Unit

  def consumableEvents_=(events: Seq[GameEvent]): Unit

  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}


trait CycleManager {
  def currentCycle_=(cycle: Int): Unit
}

object MutableGameState {


  private class GameStateImpl(startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece],
                              val gameBoard: GameBoard) extends MutableGameState with CycleManager {

    var consumableEvents: Seq[GameEvent] = Seq()

    var currentTurn: Int = 0

    var currentCycle: Int = 0

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

    override def nextPlayer: Player = nextPlayerStrategy()

  }

  def apply(startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: GameBoard): MutableGameState with CycleManager =
    new GameStateImpl(startPlayer, nextPlayerStrategy, pieces, board)
}

