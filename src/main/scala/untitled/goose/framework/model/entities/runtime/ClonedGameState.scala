package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

object ClonedGameState {

  private class ClonedGameState(state: GameState) extends GameState {

    private val clonedBoard = Board(state.gameBoard.definition)
    clonedBoard.tiles.foreach(t => t.history = state.gameBoard.tiles.find(_.definition == t.definition).get.history)

    private val clonedCurrentPlayer = clonePlayer(state.currentPlayer)

    private val clonedNextPlayer = clonePlayer(state.nextPlayer)

    override val currentTurn: Int = state.currentTurn

    override val currentCycle: Int = state.currentCycle

    override val currentPlayer: Player = clonedCurrentPlayer

    override val nextPlayer: Player = clonedNextPlayer

    override val playerPieces: Map[Player, Piece] = state.playerPieces

    override val gameBoard: Board = clonedBoard

    override val consumableBuffer: Seq[ConsumableGameEvent] = state.consumableBuffer

    override val gameHistory: Seq[GameEvent] = state.gameHistory

    private def clonePlayer(player: Player): Player = {
      val clonedPlayer = Player(player.name)
      clonedPlayer.history = player.history
      clonedPlayer
    }
  }

  /** Creates an immutable clone of a game state. Useful for asynchronous display. */
  def apply(gameState: GameState): GameState = new ClonedGameState(gameState)
}

