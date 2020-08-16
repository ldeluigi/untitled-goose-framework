package model.rules.behaviours

import engine.events.{StepMovementEvent, StopOnTileEvent, TeleportEvent, TileEnteredEvent, TileExitedEvent}
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.entities.board.{Piece, Position}
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.{Player, Tile}

case class TeleportBehaviour() extends BehaviourRule {
  override def name: Option[String] = Some("Teleport")

  override def applyRule(state: GameState): Seq[Operation] =
    state.currentPlayer.history
      .filterCurrentTurn(state)
      .filterNotConsumed()
      .filter(_.isInstanceOf[TeleportEvent])
      .map(_.asInstanceOf[TeleportEvent])
      .consumeAll()
      .flatMap(e => teleportOperation(state, state.currentPlayer, e.teleportTo))

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
