package model.game

import model.events.GameEvent
import model.events.consumable.ConsumableGameEvent

trait CycleMutableGameState extends MutableGameState {
  def currentCycle_=(cycle: Int): Unit
}

object CycleMutableGameState {

  private class GameStateImpl(startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece],
                              val gameBoard: GameBoard) extends CycleMutableGameState {

    var consumableBuffer: Seq[ConsumableGameEvent] = Seq()

    var gameHistory: Seq[GameEvent] = Seq()

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

  def apply(startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: GameBoard): CycleMutableGameState =
    new GameStateImpl(startPlayer, nextPlayerStrategy, pieces, board)
}
