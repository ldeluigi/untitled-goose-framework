package model.rules.behaviours

import engine.events.{StopOnTileEvent, TeleportEvent, TileEnteredEvent, TileExitedEvent}
import model.entities.board.{Piece, Position}
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.{Player, Tile}

/** Creates a teleport related behaviour rule. */
case class TeleportBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Teleport")

  override def applyRule(state: GameState): Seq[Operation] =
    state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .filter(_.isInstanceOf[TeleportEvent])
      .map(_.asInstanceOf[TeleportEvent])
      .consumeAll()
      .flatMap(e => teleportOperation(state, state.currentPlayer, e.teleportTo))

  /** Gives the player the ability to teleport to a certain non necessarily subsequent tile.
   *
   * @param state  the current game state
   * @param player the player to move tiles
   * @param tile   the tile the player will have to move to
   * @return the resulting sequence of operation
   */
  def teleportOperation(state: GameState, player: Player, tile: Tile): Seq[Operation] = {
    val tileExited = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(TileExitedEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val teleport = Operation.execute(state => {
      state.updatePlayerPiece(player, piece => {
        Piece(piece, Some(Position(tile)))
      })
    })

    val tileEntered = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(TileEnteredEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val tileStopped = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(StopOnTileEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    Seq(tileExited, teleport, tileEntered, tileStopped)

  }
}
