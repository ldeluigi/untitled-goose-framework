package model.game

import engine.events.GameEvent
import engine.events.consumable.ConsumableGameEvent
import model.Player
import model.entities.board.Piece

//TODO review this implementation

object ClonedGameState {

  private class ClonedGameState(state: GameState) extends GameState {
    override val currentTurn: Int = state.currentTurn

    override val currentCycle: Int = state.currentCycle

    override val currentPlayer: Player = state.currentPlayer

    override val nextPlayer: Player = state.nextPlayer

    override val playerPieces: Map[Player, Piece] = state.playerPieces

    override val gameBoard: GameBoard = state.gameBoard

    override val consumableBuffer: Seq[ConsumableGameEvent] = state.consumableBuffer

    override val gameHistory: Seq[GameEvent] = state.gameHistory
  }

  def apply(gameState: GameState): GameState = new ClonedGameState(gameState)
}

