package model.rules.operations.update

import model.events.consumable._
import model.entities.runtime.{GameState, Piece, Player, Position, Tile}
import model.entities.runtime.GameStateExtensions._
import model.rules.operations.Operation

object StepOperation {

  def apply(state: GameState, steps: Int, player: Player, forward: Boolean): Seq[Operation] = {
    (1 to steps).toList.flatMap(i => {
      stepOperation(state, player, forward, steps - i)
    })
  }

  private def stepOperation(state: GameState, player: Player, forward: Boolean, remainingSteps: Int): Seq[Operation] = {

    var opSeq: Seq[Operation] = Seq()
    opSeq = opSeq :+ Operation.triggerWhen(
      state => state.playerPieces(player).position.isDefined,
      state => Seq(TileExitedEvent(player, state.playerPieces(player).position.get.tile, state.currentTurn, state.currentCycle)))

    opSeq = opSeq ++ checkAndTriggerPassedPlayers(state, player)


    val stepFunction: (Option[Position], GameState) => Option[Tile] = (p, state) => {
      val inverted = state.consumableBuffer
        .filterCycle(state.currentCycle)
        .only[InvertMovementEvent]
        .count(_.player == player) % 2 != 0
      p match {
        case Some(pos) => if (forward) {
          if (!inverted) {
            state.gameBoard
              .next(pos.tile)
          } else {
            state.gameBoard
              .prev(pos.tile)
          }
        } else {
          if (inverted) {
            state.gameBoard
              .next(pos.tile)
          } else {
            state.gameBoard
              .prev(pos.tile)
          }
        }
        case None => Some(state.gameBoard.first)
      }
    }

    opSeq = opSeq :+ Operation.updateState(state => {
      state.updatePlayerPiece(player, piece => Piece(piece, stepFunction(state.playerPieces(player).position, state).map(Position(_))))
    })


    opSeq = opSeq :+ Operation.triggerWhen(
      s => s.playerPieces(player).position.isDefined,
      s => Seq(TileEnteredEvent(player, s.playerPieces(player).position.get.tile, state.currentTurn, state.currentCycle))
    )

    opSeq :+ Operation.triggerWhen(
      s => s.playerPieces(player).position.isDefined && remainingSteps == 0,
      s => Seq(StopOnTileEvent(player, s.playerPieces(player).position.get.tile, state.currentTurn, state.currentCycle))
    )
  }

  private def checkAndTriggerPassedPlayers(state: GameState, player: Player): Seq[Operation] = {
    val tile = state.playerPieces(player).position.map(_.tile)
    var opSeq: Seq[Operation] = Seq()
    if (tile.isDefined) {
      for (other <- state.players.toSeq if !other.equals(player)) {
        if (state.playerLastTurn(other).exists(l => state.playerStopsTurns(tile.get, other).contains(l))) {
          opSeq = opSeq :+ Operation.trigger(PlayerPassedEvent(other, player, tile.get, state.currentTurn, state.currentCycle))
        }
      }
    }
    opSeq
  }
}

