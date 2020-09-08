package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

/** A mutable game state that can set its cycle value.
 * A cycle is an iteration of a rule-solving process based on a stack of triggers.
 * It's useful to avoid re-evaluation of rules after the same trigger.
 */
private[framework] trait CycleMutableGameState extends MutableGameState {
  def currentCycle_=(cycle: Int): Unit
}

private[framework] object CycleMutableGameState {

  private class GameStateImpl(firstPlayerStrategy: Seq[Player] => Player,
                              nextPlayerStrategy: MutableGameState => Player,
                              val players: Seq[Player], pieces: Map[Player, Piece],
                              val gameBoard: Board) extends CycleMutableGameState {

    var consumableBuffer: Seq[ConsumableGameEvent] = Seq()

    var gameHistory: Seq[GameEvent] = Seq()

    var currentTurn: Int = 0

    var currentCycle: Int = 0

    private var currentTurnPlayer: Player = firstPlayerStrategy(players)

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

    /** Returns the player that is supposed to go next. */
    override def nextPlayer: Player = nextPlayerStrategy(this)
  }

  def apply(firstPlayerStrategy: Seq[Player] => Player,
            nextPlayerStrategy: MutableGameState => Player,
            players: Seq[Player], pieces: Map[Player, Piece], board: Board): CycleMutableGameState =
    new GameStateImpl(firstPlayerStrategy, nextPlayerStrategy, players, pieces, board)
}
