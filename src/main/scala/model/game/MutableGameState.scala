package model.game

import engine.events.root.GameEvent
import model.Player
import model.entities.board.Piece

/** Models the concept of a mutable game state */
trait MutableGameState extends GameState {

  /**
   * @param value the boolean value indicating if a new turn has started
   */
  def newTurnStarted_=(value: Boolean): Unit

  /**
   * @param player the current player to be set as playing player
   */
  def currentPlayer_=(player: Player): Unit

  /**
   * @param turn the turn to be set as the starting turn
   */
  def currentTurn_=(turn: Int): Unit

  /**
   * @param history the game's past event history
   */
  def history_=(history: List[GameEvent]): Unit

  /**
   * @param player the player owning the piece to update
   * @param update the piece of the player to be updated
   */
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

  /** A factoru creating a ew mutable game state based on a turn, a starting plaer,
   * a strategy to select the player stategu, the map of pieces and players and the game's board. */
  def apply(startTurn: Int, startPlayer: Player, nextPlayerStrategy: () => Player, pieces: Map[Player, Piece], board: GameBoard): MutableGameState =
    new GameStateImpl(startTurn, startPlayer, nextPlayerStrategy, pieces, board)
}

