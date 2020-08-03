package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.entities.board.Position
import model.{MatchState, Player, Tile}

class TeleportOperation(player: Player, tile: Tile) extends Operation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {
    state.updatePlayerPiece(player, piece =>
      piece.updatePosition(_ => Some(Position(tile)))
    )
  }
}

object TeleportOperation{
  def apply(player: Player, tile: Tile): TeleportOperation = new TeleportOperation(player, tile)
}
