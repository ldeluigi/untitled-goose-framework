package model.rules.operations.update

import engine.events.consumable.{StopOnTileEvent, TileEnteredEvent, TileLeftEvent}
import model.entities.board.{Piece, Position}
import model.game.GameState
import model.rules.operations.Operation
import model.{Player, Tile}

object TeleportOperation {
  def apply(state: GameState, player: Player, tile: Tile): Seq[Operation] = {

    var opSeq: Seq[Operation] = Seq()
    val tileLeft = state.playerPieces(player).position.map(_.tile)

    if (tileLeft.isDefined) {
      opSeq = opSeq :+ Operation.trigger(TileLeftEvent(player, tileLeft.get, state.currentTurn, state.currentCycle))
    }

    opSeq = opSeq :+ Operation.updateState(state => {
      state.updatePlayerPiece(player, piece => {
        Piece(piece, Some(Position(tile)))
      })
    })

    opSeq :+ Operation.trigger(
      TileEnteredEvent(player, tile, state.currentTurn, state.currentCycle),
      StopOnTileEvent(player, tile, state.currentTurn, state.currentCycle)
    )
  }

}
