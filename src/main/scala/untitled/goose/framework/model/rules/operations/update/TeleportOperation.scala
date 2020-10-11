package untitled.goose.framework.model.rules.operations.update

import untitled.goose.framework.model.entities.runtime._
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.events.consumable.{StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import untitled.goose.framework.model.rules.operations.Operation

object TeleportOperation {

  /**
   * Creates a teleport operation sequence with given parameters.
   *
   * @param state  the current game state.
   * @param player the player that should teleport.
   * @param tile   the tile to which the player should teleport.
   * @return a sequence of operations to do.
   */
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
