package model.rules.behaviours

import engine.events.{StopOnTileEvent, TeleportEvent, TileEnteredEvent, TileExitedEvent}
import model.entities.board.Position
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.{MatchState, Player, Tile}

class TeleportBehaviour extends BehaviourRule {
  override def name: Option[String] = Some("Teleport")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[TeleportEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[TeleportEvent]
      })
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
        piece.updatePosition(_ => Some(Position(tile)))
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
