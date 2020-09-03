package model.rules.operations.update

import model.events.consumable.{StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import model.entities.runtime.{GameState, Piece, Player, Position, Tile}
import model.rules.operations.Operation

object TeleportOperation {
  def apply(state: GameState, player: Player, tile: Tile): Seq[Operation] = {

    var opSeq: Seq[Operation] = Seq()
    val tileExited = state.playerPieces(player).position.map(_.tile)

    if (tileExited.isDefined) {
      opSeq = opSeq :+ Operation.trigger(TileExitedEvent(player, tileExited.get, state.currentTurn, state.currentCycle))
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
