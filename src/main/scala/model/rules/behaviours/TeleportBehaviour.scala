package model.rules.behaviours

import engine.events.{StopOnTileEvent, TeleportEvent, TileEnteredEvent, TileExitedEvent}
import model.`match`.MatchState
import model.`match`.MatchStateExtensions.PimpedHistory
import model.entities.board.{Piece, Position}
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.{Player, Tile}

class TeleportBehaviour extends BehaviourRule {
  override def name: Option[String] = Some("Teleport")

  override def applyRule(implicit state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filterCurrentTurn()
      .filterNotConsumed()
      .only[TeleportEvent]()
      .consumeAll()
      .flatMap(e => teleportOperation(state, state.currentPlayer, e.teleportTo))
  }

  def teleportOperation(state: MatchState, player: Player, tile: Tile): Seq[Operation] = {
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
